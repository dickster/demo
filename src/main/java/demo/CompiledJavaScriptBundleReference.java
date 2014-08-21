package demo;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.bundles.IResourceBundle;
import org.apache.wicket.util.lang.Args;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class CompiledJavaScriptBundleReference extends ResourceReference implements IResourceBundle {

    private final List<JavaScriptReferenceHeaderItem> providedResources;

    private IJavaScriptCompiler compiler;
    private CompiledBundleResource resource;

    public CompiledJavaScriptBundleReference(Class<?> scope, String name, List<JavaScriptReferenceHeaderItem> resources) {
        super(scope, name, null, null, null);
        providedResources = Args.notNull(resources, "resources");
    }

    public CompiledJavaScriptBundleReference(Class<?> scope, String name, JavaScriptReferenceHeaderItem... resources) {
        this(scope, name, Arrays.asList(resources));
    }

    @Override
    public IResource getResource() {
        if (resource==null) {
            resource = new CompiledBundleResource(getName(), providedResources).withCompiler(compiler);
        }
        return resource;
    }


    @Override
    public List<JavaScriptReferenceHeaderItem> getProvidedResources() {
        return providedResources;  // TODO : Make immutable.
    }

    @Override
    public Iterable<? extends HeaderItem> getDependencies() {
        Set<HeaderItem> ret = new LinkedHashSet<HeaderItem>();
        for (HeaderItem curProvided : providedResources) {
            for (HeaderItem curDependency : curProvided.getDependencies())
                ret.add(curDependency);
        }
        for (HeaderItem curProvided : providedResources) {
            ret.remove(curProvided);
        }
        return ret;
    }

    public CompiledJavaScriptBundleReference withCompiler(IJavaScriptCompiler compiler) {
        this.compiler = compiler;
        return this;
    }

    public boolean contains(JavaScriptReferenceHeaderItem item) {
        return providedResources.contains(item);
    }
}
