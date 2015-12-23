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

    public Template(String id, String source) {
        super(id);
        this.source = source;
    }

    @Override
    public Markup getAssociatedMarkup() {
        // TODO : if source beginswith("/templates")...blah blah blah.
        try {
            InputStream stream = new ClassPathResource("demo/resources/templates/"+source).getInputStream();
            String content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
            Closeables.closeQuietly(stream);
            return Markup.of(content);
        } catch (IOException e) {
            e.printStackTrace();
            return Markup.of("<div class='error'>error reading markup for " + source + "</div>");
        }
    }
}
