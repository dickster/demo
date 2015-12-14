package forms.widgets;

import forms.config.Config;
import forms.config.HasConfig;
import forms.config.TextFieldConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;

public class TextField2<T> extends TextField<T> implements HasConfig {



    private Config config;

    public TextField2(String id, TextFieldConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "text");
        super.onComponentTag(tag);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }


}
