package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;

public class Demo5FormConfig extends FormConfig {

    public Demo5FormConfig() {
        super("demo5");
        withTitle("Ajax & Behavior Examples");
        addConfigs();
    }

    private void addConfigs() {
        with(new LabelConfig(" ....type ahead, dependencies, postal code & coverage"));
        with(new ButtonConfig("button.next"));
        with(new ButtonConfig("button.cancel"));
    }


}
