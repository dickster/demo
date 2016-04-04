package forms.impl;

import com.google.common.collect.Lists;
import forms.widgets.config.*;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public class Demo6AFormConfig extends FormConfig {

    public Demo6AFormConfig() {
        super("demo6");
        withTitle("Validations");
        addConfigs();
        //withTemplate("demo6.html");
    }

    private void addConfigs() {
        with(new LabelConfig("label.usesCC").withId("label").withTagName("p"));

        with(new LabelConfig("label.email" ));
        with(new LabelConfig("email", "insured.contact.email"));
        with(new LabelConfig("label.phone"));
        with(new LabelConfig("phone", "insured.contact.phone"));

        with(new ButtonConfig("button.next"));
    }}
