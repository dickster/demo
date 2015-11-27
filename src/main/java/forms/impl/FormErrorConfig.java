package forms.impl;

import forms.config.ButtonConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;
import forms.config.TextFieldConfig;

public class FormErrorConfig extends FormConfig {

    public static final String FORM_ERROR = "FORM-ERROR";

    public FormErrorConfig() {
        super(FORM_ERROR);
        withTitle("Form Error");
        addConfigs();
    }

    private void addConfigs() {
        with(new LabelConfig("your credit card has expired").withCss("error-message"));
        with(new LabelConfig("please enter a new number"));
        with(new TextFieldConfig("insured.cc"));
        with(new ButtonConfig("ok"));

    }
}
