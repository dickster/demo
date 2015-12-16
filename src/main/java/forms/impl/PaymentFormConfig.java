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
        with(new LabelConfig("payment method"));
        withConfig(new SelectPickerConfig("payment.method")
                .withOptions(Lists.newArrayList("Credit Card", "Cash", "Direct Billing"))
                .withAjaxHandler("creditCardAjaxHandler"));
    // TODO : add some kind of ajax listener here...when # of digits is 12 then do ajax call?
    // and enable submit button otherwise show error!
    // need to write a field error widget in jquery Ui/bootstrap

        with( new DivConfig("creditCardDiv")
                .withConfigs(
                        new LabelConfig("credit card number"),
                        new TextFieldConfig<Integer>("payment.cc"),

                        new LabelConfig("expiry date"),
                        new TextFieldConfig("payment.expiry"),

                        new LabelConfig("security code"),
                        new TextFieldConfig<Integer>("payment.securityCode"),

                        new LabelConfig("payment frequency"),
                        new SelectPickerConfig<String>("payment.frequency")
                                .withOptions(Lists.newArrayList(
                                        "6 months",
                                        "1 year",
                                        "montly",
                                        "daily",
                                        "send the bill to my mother in law"
                                )))
                    .initiallyVisible(false)
                );

        with(new ButtonConfig("next"));
    }
}
