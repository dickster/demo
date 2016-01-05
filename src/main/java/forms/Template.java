package forms;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.panel.Panel;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Template extends Panel {

    // TODO : add caching for this. maybe (re)use wicket's markupCache or
    // create my own spring bean based on guava??? private @Inject MarkupCache cache;

    private String source;
    private static final String TEMPLATE_BASE = "demo/resources/templates/";

    // usage note : you must decorate this with class="template-source" and the previous element (which contains all
    // the elements mapped out in the template) must be class="template-data".  if not, the .js will not find it.
    public Template(String id, String source) {
        super(id);
        setOutputMarkupId(true);
        setRenderBodyOnly(false);
        this.source = normalize(source);
    }

    @Override
    public boolean isVisible() {
        return source!=null;
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
            source = source+".html";
            System.out.println("assuming you want to load an .html file.  adding extension implicitly to " + source);
        }
        return source;
    }
    @Override
    public Markup getAssociatedMarkup() {
        try {
            if (source==null) {
                return Markup.of("<wicket:panel><div class='raw-content'>empty default template</div></wicket:panel>");
            }
            InputStream stream = new ClassPathResource(getFullPath()).getInputStream();
            String content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
            Closeables.closeQuietly(stream);
            return Markup.of(content);
        } catch (IOException e) {
            e.printStackTrace();  // TODO : how to handle
            return Markup.of("<wicket:panel><div class='raw-content template-error'>error reading markup for "+getFullPath()+"</div></wicket:panel>");
        }
    }

    private String getFullPath() {
        return TEMPLATE_BASE+source;
    }
}
