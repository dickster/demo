package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;

public class Demo4FormConfig extends FormConfig {

    public Demo4FormConfig() {
        super("demo4");
        withTitle("Section Panels");
        addConfigs();
    }

    private void addConfigs() {
        with(new LabelConfig("validations"));
        with(new ButtonConfig("button.next"));
        with(new ButtonConfig("button.cancel"));
    }


}
