package forms.widgets;

import forms.widgets.config.HasConfig;
import forms.widgets.config.TextFieldConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;

public class TextField2<T> extends TextField<T> implements HasConfig<TextFieldConfig<T>> {

    private TextFieldConfig<T> config;

    public TextField2(String id, TextFieldConfig<T> config) {
        super(id);
        this.config = config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
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
    public TextFieldConfig<T> getConfig() {
        return this.config;
    }

}
