package forms;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import forms.spring.StringLoader;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.panel.Panel;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicMarkup extends Panel {

    private static final String TEMPLATE_BASE = "demo/resources/templates/";

    // TODO : add caching for this. maybe (re)use wicket's markupCache or
    // create my own spring bean based on guava??? private @Inject MarkupCache cache;

    private @Inject StringLoader loader;

    private String source;

    public DynamicMarkup(String id, String template) {
        super(id);
        setOutputMarkupId(true);
        setRenderBodyOnly(false);
        this.source = normalize(template);
    }

    private String normalize(String source) {
        if (source==null) {
            return null;
        }
        source = source.trim();
        if (source.startsWith(TEMPLATE_BASE)) {
            source = source.substring(TEMPLATE_BASE.length());
        }
        if (!source.endsWith(".html") && source.indexOf('.')==-1) {
            System.out.println("WARNING : assuming you want to load an .html template file. i.e. [" + source +".html] as opposed to ["+source+"]");
            source = source+".html";
        }
        return source;
    }

    @Override
    public Markup getAssociatedMarkup() {
        try {
            if (source==null) {
                // this should never happen!  if source==null, setVisible(false).
                throw  new IllegalStateException("attempting to render an null/empty template.");
            }
            InputStream stream = new ClassPathResource(getFullPath()).getInputStream();
            String content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
            Closeables.closeQuietly(stream);
            return Markup.of(resolveLabels(content));
        } catch (IOException e) {
            throw new WorkflowException(e);
        }
    }

    private String resolveLabels(String content) {
//            if (loader!=null) {
        // TODO : replace ${label.foo} with values stored in string loader.
        //  e.g. ${name} --> "derek"
        // this should be locale sensitive.
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(content);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);   // use loader to get values.
            String value = loader.get(key);
            matcher.appendReplacement(sb, value==null ? "["+key+"]" : value);
        }
        //          }
        return matcher.appendTail(sb).toString();
    }

    private String getFullPath() {
        return TEMPLATE_BASE+source;
    }

    public DynamicMarkup withLabelResources(StringLoader loader) {
        this.loader = loader;
        return this;
    }

}
