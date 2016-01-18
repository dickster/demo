package forms.widgets;

import forms.widgets.config.Config;
import forms.widgets.config.CreditCardTextFieldConfig;
import forms.widgets.config.HasConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;

public class CreditCardTextField extends TextField<String> implements HasConfig {

    private final CreditCardTextFieldConfig config;

    public CreditCardTextField(String id, CreditCardTextFieldConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "text");
        super.onComponentTag(tag);
    }

}
