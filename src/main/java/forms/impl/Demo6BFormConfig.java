package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;

public class Demo6BFormConfig extends FormConfig {

    public Demo6BFormConfig() {
        super("demo6B");
        withTitle("Validations");
        addConfigs();
        //withTemplate("demo6.html");
    }

    private void addConfigs() {
        with(new LabelConfig("label.usesCash").withId("label").withTagName("p"));

        with(new LabelConfig("label.email"));
        with(new LabelConfig("email", "insured.contact.email"));
        with(new LabelConfig("label.phone"));
        with(new LabelConfig("phone", "insured.contact.phone"));

        with(new ButtonConfig("button.next"));
    }}
