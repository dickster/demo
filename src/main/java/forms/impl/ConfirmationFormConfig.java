package forms.impl;

import forms.config.ButtonConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;

public class ConfirmationFormConfig extends FormConfig {

    public ConfirmationFormConfig() {
        super("Confirm-Form");
        addConfigs();
        withTitle("Confirmation");
    }

    private void addConfigs() {
        withConfig(new LabelConfig("Thanks for your payment of ").withCss("h1"));
        withConfig(new LabelConfig("payment.total" /**, format="$ddd.dd"**/).withCss("h1"));
        with(new ButtonConfig("finished"));
    }
}
