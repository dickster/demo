
package demo;

import com.google.common.collect.Maps;
import org.apache.wicket.markup.head.IReferenceHeaderItem;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.caching.IStaticCacheableResource;
import org.apache.wicket.util.io.ByteArrayOutputStream;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;


public class CompiledBundleResource extends AbstractResource implements IStaticCacheableResource {

    private final List<? extends IReferenceHeaderItem> providedResources;
    private final String name;

    private boolean cachingEnabled;

    private IJavaScriptCompiler compiler = new ConcatenatingCompiler();


    public CompiledBundleResource(String name, List<? extends IReferenceHeaderItem> providedResources) {
        this.providedResources = Args.notNull(providedResources, "providedResources");
        cachingEnabled = true;
        this.name = name;
    }

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
        final ResourceResponse resourceResponse = new ResourceResponse();

        if (resourceResponse.dataNeedsToBeWritten(attributes)) {
            try {
                ValidationResult result = validateResourceStreams();
                if (!result.isValid()) {
                    return sendResourceError(resourceResponse, 404/*SC_NOT_FOUND*/, "Unable to find resource");
                }

                resourceResponse.setContentType(result.getContentType());

                // add Last-Modified header (to support HEAD requests and If-Modified-Since)
                final Time lastModified = result.getLastModified();

                if (lastModified != null)
                    resourceResponse.setLastModified(lastModified);

                // read resource data
                final byte[] bytes = readAllResources(result.getStreams());

                // send Content-Length header
                resourceResponse.setContentLength(bytes.length);

                // send response body with resource data
                resourceResponse.setWriteCallback(new WriteCallback() {
                    @Override
                    public void writeData(Attributes attributes) {
                        attributes.getResponse().write(bytes);
                    }
                });
            } catch (IOException e) {
                return sendResourceError(resourceResponse, 500, "Unable to read resource stream");
            } catch (ResourceStreamNotFoundException e) {
                return sendResourceError(resourceResponse, 500, "Unable to open resource stream");
            }
        }

        return resourceResponse;
    }

    private ValidationResult validateResourceStreams() {
        ValidationResult result = new ValidationResult();
        for (IReferenceHeaderItem curItem : providedResources) {
            result.validate(curItem);
        }
        return result;
    }

    protected byte[] readAllResources(Map<String,IResourceStream> resources) throws IOException, ResourceStreamNotFoundException {
        return getCompiler().compile(resources);
    }

    private ResourceResponse sendResourceError(ResourceResponse resourceResponse, int errorCode, String errorMessage) {
        resourceResponse.setError(errorCode, errorMessage);
        return resourceResponse;
    }

    @Override
    public boolean isCachingEnabled() {
        return cachingEnabled;
    }

    public void setCachingEnabled(final boolean enabled) {
        cachingEnabled = enabled;
    }

    @Override
    public Serializable getCacheKey() {
        ArrayList<Serializable> key = new ArrayList<Serializable>(providedResources.size());
        for (IReferenceHeaderItem curItem : providedResources) {
            Serializable curKey = ((IStaticCacheableResource)curItem.getReference().getResource()).getCacheKey();
            if (curKey == null) {
                throw newExceptionFor("Unable to getTarget cache key for ",curItem);
            }
            key.add(curKey);
        }
        return key;
    }

    @Override
    public IResourceStream getCacheableResourceStream() {
        ValidationResult result = validateResourceStreams();
        if (!result.isValid()) {
            return null;
        }

        byte[] bytes = null;
        try {
            bytes = readAllResources(result.getStreams());
        } catch (IOException e) {
            return null;
        } catch (ResourceStreamNotFoundException e) {
            return null;
        }

        final String contentType = result.getContentType();
        final Time lastModified = result.getLastModified();
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        final long length = bytes.length;
        AbstractResourceStream ret = new AbstractResourceStream() {
            private static final long serialVersionUID = 1L;

            @Override
            public InputStream getInputStream() throws ResourceStreamNotFoundException {
                return inputStream;
            }

            @Override
            public Bytes length() {
                return Bytes.bytes(length);
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public Time lastModifiedTime() {
                return lastModified;
            }

            @Override
            public void close() throws IOException {
                inputStream.close();
            }
        };
        return ret;
    }

    private byte[] concatenate(Map<String,IResourceStream> resources) throws ResourceStreamNotFoundException, IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (IResourceStream curStream : resources.values()) {
            IOUtils.copy(curStream.getInputStream(), output);
        }
        return output.toByteArray();
    }

    private MissingResourceException newExceptionFor(String prefix,IReferenceHeaderItem item) {
        ResourceReference reference = item.getReference();
        String scope = Classes.name(reference.getScope());
        String name = reference.getName();
        StringBuilder message = new StringBuilder(prefix);
        message.append(scope).append('/').append(name);
        return new MissingResourceException(message.toString(), scope, name);
    }


    public CompiledBundleResource withCompiler(IJavaScriptCompiler compiler) {
        this.compiler = compiler;
        return this;
    }

    protected IJavaScriptCompiler getCompiler() {
        return compiler;
    }


    class ConcatenatingCompiler implements IJavaScriptCompiler {
        private byte[] bytes;

        @Override public byte[] compile(Map<String, IResourceStream> items) throws ResourceStreamNotFoundException, IOException {
            if (bytes==null) {
                bytes = concatenate(items);
            }
            return bytes;
        }
    }


    class ValidationResult {
        Exception exception;
        String contentType;
        Time lastModified;
        Map<String,IResourceStream> streams = Maps.newHashMap();

        public ValidationResult() {
        }

        public String getContentType() {
            return contentType;
        }

        public Time getLastModified() {
            return lastModified;
        }

        public void validate(IReferenceHeaderItem item) throws MissingResourceException {
            IResourceStream stream = ((IStaticCacheableResource)item.getReference().getResource()).getCacheableResourceStream();
            if (stream==null) {
                throw newExceptionFor("can't find stream for ", item);
            }
            streams.put(item.getReference().getName(), stream);
            Time curLastModified = stream.lastModifiedTime();
            if (lastModified == null || curLastModified.after(lastModified)) {
                lastModified = curLastModified;
            }
            contentType = stream.getContentType();
        }

        public boolean isValid() {
            return exception == null;
        }

        public Map<String,IResourceStream> getStreams() {
            return streams;
        }
    }

}
