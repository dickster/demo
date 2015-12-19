package forms.widgets;

import forms.config.Config;
import forms.config.CreditCardTextFieldConfig;
import forms.config.HasConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;

public class CreditCardTextField extends TextField<Long> implements HasConfig {

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
