package forms.impl;

import com.google.common.collect.Lists;
import forms.config.*;

public class PaymentFormConfig extends FormConfig {

    public PaymentFormConfig() {
        super("Payment-Form");
        addConfigs();
        withTitle("Payment Information");
    }

    private void addConfigs() {
        withConfig(new SelectPickerConfig("payment.method")
                .withChoices(Lists.newArrayList("Credit Card", "Cash", "Direct Billing")));
    // TODO : add some kind of ajax listener here...when # of digits is 12 then do ajax call?
    // and enable submit button otherwise show error!

    // need to write a field error widget in jquery Ui/bootstrap

        withConfig(new LabelConfig("credit card number"));
        withConfig(new TextFieldConfig<Integer>("payment.cc"));

        withConfig(new LabelConfig("expiry date"));
        withConfig(new TextFieldConfig("payment.expiry"));  // TODO : change to date picker!!!

        withConfig(new LabelConfig("security code"));
        withConfig(new TextFieldConfig<Integer>("payment.securityCode"));

        withConfig(new SelectPickerConfig<String>("payment.frequency")
                .withChoices(Lists.newArrayList(
                        "6 months",
                        "1 year",
                        "montly",
                        "daily",
                        "send the bill to my mother in law"
                )));

        with(new ButtonConfig("next"));
    }
}
