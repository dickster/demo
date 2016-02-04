package forms.impl;

import com.google.common.collect.Lists;
import forms.widgets.config.*;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public class Demo5FormConfig extends FormConfig {

    public Demo5FormConfig() {
        super("demo5");
        withTitle("Payment Information");
        withTitle("Ajax & Behavior Examples");
        addConfigs();
        withTemplate("demo5.html");
    }

    private void addConfigs() {
        with(new LabelConfig("label.email"));
        with(new TextFieldConfig("insured.contact.email")
                .addValidator(EmailAddressValidator.getInstance())
                .withBehavior("emailAjaxBehavior")
            );

        with(new LabelConfig("label.phone"));
        with(new PhoneNumberConfig("insured.contact.phone")
                .withBehavior("phoneNumberAjaxBehavior"));


        withConfig(new CheckBoxConfig("insured.notifyMe", "Notify me when my policy is about to expire.")
                    .initiallyHidden());

        withConfig(new CheckBoxConfig("insured.smokes", "I wish to apply for extra 416 coverage")
                    .initiallyHidden());

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
    }}
