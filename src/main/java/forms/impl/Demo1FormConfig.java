package forms.impl;

import com.google.common.collect.Lists;
import forms.widgets.config.*;

public class Demo1FormConfig extends FormConfig {

    public Demo1FormConfig() {
        super("demo1");
        withTitle("Unformatted Form");
        addConfigs();
    }

    private void addConfigs() {
        withConfig(new CheckBoxConfig("insured.smokes", "i have read the terms of agreement"));

        withConfig(new SelectPickerConfig<String>("name.salutation")
                .withOptions(Lists.newArrayList("Mr.", "Mrs.", "Ms", "Dr.")));

        withConfig(new LabelConfig("label.first"));
        withConfig(new LabelConfig("label.middle"));
        withConfig(new LabelConfig("label.last"));
        withConfig(new TextFieldConfig("name.first"));
        withConfig(new TextFieldConfig("name.middle"));
        withConfig(new TextFieldConfig("name.last"));

        withConfig(new LabelConfig("label.country"));
        withConfig(new TextFieldConfig("insured.country"));


        with(new LabelConfig("label.paymentMethod"));
        withConfig(new SelectPickerConfig("payment.method")
                .withOptions(Lists.newArrayList("MasterCard", "Visa", "Diner's Club"))
                .withDependentsFor(0, "label.one")
                .withDependentsFor(1, "label.two")
                .withDependentsFor(2, "label.three"));

        with(new LabelConfig("label.one"));
        with(new LabelConfig("label.two"));
        with(new LabelConfig("label.three"));

        withConfig(new LabelConfig("label.vehicleType"));
        withConfig(new TextFieldConfig<Integer>("vehicle.type"));

        with(new ButtonConfig("button.next"));
    }


}
