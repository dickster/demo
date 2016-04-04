package forms.impl;

import com.google.common.collect.Lists;
import forms.widgets.config.*;

public class PaymentFormConfig extends FormConfig {

    public PaymentFormConfig() {
        super("Payment-Form");
        addConfigs();
        withTitle("Payment Information");
        //withTemplate("payment.html");
    }

    private void addConfigs() {
        with(new LabelConfig("label.paymentMethod"));
        withConfig(new SelectPickerConfig("payment.method")
                .withOptions(Lists.newArrayList("Credit Card", "Cash", "Direct Billing"))
                .withDependentsFor(0,
                        "label.ccNumber",
                        "payment.cc",
                        "label.ccExpiry",
                        "payment.expiry",
                        "label.ccSecurity",
                        "payment.securityCode",
                        "label.paymentFrequency",
                        "payment.frequency"
                        )
                );

        withConfigs(
                new LabelConfig("label.ccNumber"),
                new CreditCardTextFieldConfig("payment.cc"),

                new LabelConfig("label.ccExpiry"),
                new TextFieldConfig("payment.expiry"),

                new LabelConfig("label.ccSecurity"),
                new TextFieldConfig<Integer>("payment.securityCode"),

                new LabelConfig("label.paymentFrequency"),
                new SelectPickerConfig<String>("payment.frequency")
                        .withOptions(Lists.newArrayList(
                                "6 months",
                                "1 year",
                                "montly",
                                "daily",
                                "send the bill to my mother in law"
                        )));

        with(new ButtonConfig("button.next"));
    }
}
