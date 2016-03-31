package forms;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import forms.widgets.config.Config;
import forms.widgets.config.FormConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DefaultTemplateGenerator implements ITemplateGenerator {

    private final Source source;

    // TODO : add default source = html{"<head><body>${content}</body></head>}

    private static final String TAG = "<%s wicket:id='%s'/></%s>";

    public DefaultTemplateGenerator(String html) {
        this.source = new Source(html);
    }

    public DefaultTemplateGenerator(File file) {
        this.source = new Source(file);
    }

    @Override
    public String generate(FormConfig config) {
        // assert html contains "${content}"
        return generate(source.getHtml(), config);
    }

    protected String generate(String html, FormConfig config) {
        StringBuilder builder = new StringBuilder();
        List<Config> ooo = config.getConfigs();

        // huh?
        for (Config c:ooo) {
            builder.append(generate(c));
        }

        return html.replace("${content}", builder.toString());
    }

    protected String generate(Config c) {
        // decide when to put in a new row...?
        // if <input> end row?
        //push(row);

        return String.format(TAG, c.getTagName(), c.getId(), c.getTagName());
    }


    static class Source {

        private String html;
        private File file;

        public Source(String html) {
            this.html = html;
        }
        public Source(File file) {
            this.file = file;
        }

        public String getHtml() {
            try {
                return html!=null ? html : Files.toString(file, Charsets.UTF_8);
            } catch (IOException e) {
                return "<illegal template " + file.getAbsolutePath() + "> ${content}";
            }
        }
    }
}
