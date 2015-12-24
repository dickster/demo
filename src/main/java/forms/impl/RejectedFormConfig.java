package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;

public class RejectedFormConfig extends FormConfig {
    public RejectedFormConfig() {
        super("REJECTED-FORM");
        with(new LabelConfig("we currently do not do business in your country."));
        with(new ButtonConfig("ok"));
    }
}
