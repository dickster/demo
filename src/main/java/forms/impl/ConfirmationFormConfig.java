package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;

public class ConfirmationFormConfig extends FormConfig {

    public ConfirmationFormConfig() {
        super("Confirm-Form");
        addConfigs();
        withTitle("Confirmation");
        withTemplate("confirmation");
    }

    private void addConfigs() {
        withConfig(new LabelConfig("label.thanks").withCss("h4"));
        withConfig(new LabelConfig("data.total","payment.total").withCss("h4"));
        with(new ButtonConfig("button.finished"));
    }
}
