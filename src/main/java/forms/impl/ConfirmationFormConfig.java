package forms.impl;

import forms.config.ButtonConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;

import java.text.DecimalFormat;

public class ConfirmationFormConfig extends FormConfig {

    public ConfirmationFormConfig() {
        super("Confirm-Form");
        addConfigs();
        withTitle("Confirmation");
    }

    private void addConfigs() {
        withConfig(new LabelConfig("Thanks for your payment of ").withCss("h4"));
        withConfig(new LabelConfig("payment.total", new DecimalFormat("$0.00")).withCss("h4"));
        with(new ButtonConfig("finished"));
    }
}
