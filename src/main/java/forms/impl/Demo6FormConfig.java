package forms.impl;

import com.google.common.collect.Lists;
import forms.widgets.config.*;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public class Demo6FormConfig extends FormConfig {

    public Demo6FormConfig() {
        super("demo6");
        withTitle("Validations");
        addConfigs();
        withTemplate("demo6.html");
    }

    private void addConfigs() {
        with(new LabelConfig("label.usesCC"));
        with(new LabelConfig("label.usesCash"));

        with(new LabelConfig("label.email" ));
        with(new LabelConfig("email", "insured.contact.email"));
        with(new LabelConfig("label.phone"));
        with(new LabelConfig("phone", "insured.contact.phone"));

        with(new ButtonConfig("button.next"));
    }}
