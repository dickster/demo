package forms;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import forms.widgets.config.GroupConfig;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.head.IHeaderResponse;
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

    public Template(String id, GroupConfig config) {
        super(id);
        setOutputMarkupId(true);
        setRenderBodyOnly(false);
        this.source = normalize(config.getTemplate());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
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
            return Markup.of(content);
        } catch (IOException e) {
            throw new WorkflowException(e);
        }
    }

    private String getFullPath() {
        return TEMPLATE_BASE+source;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }
}
