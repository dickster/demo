package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;

public class ConfirmationFormConfig extends FormConfig {

    public ConfirmationFormConfig() {
        super("Confirm-Form");
        addConfigs();
        withTitle("Confirmation");
    }

    private void addConfigs() {
        withConfig(new LabelConfig("Thanks for your payment of ").withCss("h4"));
        withConfig(new LabelConfig("payment.total").withCss("h4"));
        with(new ButtonConfig("finished"));
    }
}
