package forms.impl;

import com.google.common.collect.Lists;
import forms.widgets.config.*;

public class Demo4FormConfig extends FormConfig {

    public Demo4FormConfig() {
        super("demo4");
        withTitle("Section Panels");
        addConfigs();
    }

    private void addConfigs() {
        with(new SectionPanelConfig("names")
                .withAtLeastOne()
                .withMax(2)
                .withAddTooltip("Add Person")
                .withTitleForNewValues("New Person")
                .withTitleInputs("first", "last")
                .withConfigs(
                        new LabelConfig("label.first"),
                        new TextFieldConfig("first"),
                        new LabelConfig("label.middle"),
                        new TextFieldConfig("middle"),
                        new LabelConfig("label.last"),
                        new TextFieldConfig("last"))
                .withTemplate("insured")
                .withTitle("Insured"));

        with(new SectionPanelConfig("vehicles")
                .withMax(4)
                .withAddTooltip("Add Vehicle")
                .withTitleForNewValues("New Vehicle")
                // TODO : doesn't work for select [type] in this case????.
                .withTitleInputs("year", "model")
                .withConfigs(
                        new LabelConfig("label.vehicleType"),
                        new SelectPickerConfig("type")
                                .withSearchingAllowed()
                                .withOptions(Lists.newArrayList("Ford", "GM", "Chrysler", "BMW", "Volkswagen", "Hyundai", "Honda", "Toyota", "Subaru", "Kia", "Mercedes", "Tesla", "Smart")),
                        new LabelConfig("label.year"),
                        new TextFieldConfig("year"),
                        new LabelConfig("label.model"),
                        new TextFieldConfig("model")
                        )
                .withTemplate("vehicles")
                .withTitle("Vehicles"));

        with(new ButtonConfig("button.next"));
        // this button is not handled.
        with(new ButtonConfig("button.cancel"));
    }


}
