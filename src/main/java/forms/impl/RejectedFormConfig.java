package forms.impl;

import forms.config.ButtonConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;

public class RejectedFormConfig extends FormConfig {
    public RejectedFormConfig() {
        super("REJECTED-FORM");
        with(new LabelConfig("we currently do not do business in your country."));
        with(new ButtonConfig("ok"));
    }
}
