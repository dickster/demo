package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;
import forms.widgets.config.TextFieldConfig;

public class InfoFormConfig extends FormConfig {

    public InfoFormConfig() {
        super("INFO_FORM");
        addConfigs();
        withTitle("Client Information");
        withTemplate("f2.html");  //withTemplate("f1");
    }

    private void addConfigs() {
//        withConfig(new LabelConfig(".").name("spacer"));
//        withConfig(new SelectPickerConfig<String>("name.salutation")
//                .withOptions(Lists.newArrayList("Mr.", "Mrs.", "Ms", "Dr.")));

//        withConfig(new LabelConfig("label.address"));
//        withConfig(new AddressConfig("insured.address"));

//        with(new SectionPanelConfig("names")
//                    .withAddTooltip("Add Person")
//                    .withTitleForNewValues("New Person")
//                    .withTitleInputs("first", "last")
//                    .withTemplate("_names")
//                    .withConfigs(
//                            new LabelConfig("label.first"),
//                            new TextFieldConfig("first"),
//                            new LabelConfig("label.middle"),
//                            new TextFieldConfig("middle"),
//                            new LabelConfig("label.last"),
//                            new TextFieldConfig("last"))
//                    .withTitle("Drivers"));

//        withConfig(new LabelConfig("label.address"));  // not in template.

        withConfig(new LabelConfig("label.first"));
        withConfig(new TextFieldConfig("name.first"));
        withConfig(new LabelConfig("label.middle"));
        withConfig(new TextFieldConfig("name.middle"));
        withConfig(new LabelConfig("label.last"));
        withConfig(new TextFieldConfig("name.last"));

//        withConfig(new LabelConfig("label.age"));
//        withConfig(new TextFieldConfig<Integer>("insured.age").withAjaxBehavior("ageOccupationAjaxBehavior"));
//        withConfig(new LabelConfig("label.occupation"));
//        withConfig(new TextFieldConfig<Integer>("insured.occupation"));

//        withConfig(new LabelConfig("l1", "name.first"));
//        withConfig(new LabelConfig("label.names").withData("name.first", "name.last"));
//        withConfig(new LabelConfig("label.bogus").withData("name.first").withFormatter("specialFormatter"));
//
////        withConfig(new CheckBoxConfig("insured.smokes", "i smoke cigarettes"));
//
//        withConfig(new LabelConfig("label.address"));
//        withConfig(new AddressConfig("insured.address"));

//        withConfig(new LabelConfig("address 2"));
//        withConfig(new AddressConfig("insured.address2"));
//
//        withConfig(new LabelConfig("contact info"));
//        withConfig(new TextFieldConfig("insured.contact.email"));
//
//        withConfig(new LabelConfig("drivers license"));
//        withConfig(new TextFieldConfig("insured.driversLicense"));
//
//        withConfig(new LabelConfig("vehicle type"));
//        withConfig(new TextFieldConfig("vehicle.type"));
//
//        withConfig(new LabelConfig("vehicle year"));
//        withConfig(new SelectPickerConfig<Integer>("vehicle.year")
//                // this would typically be in a spring bean/ NOT inlined.
//                .withOptions(new SelectOptionsService<Integer>() {
//                    @Override
//                    public List<Integer> getOptions() {
//                        int startYear = (int) (Math.random() * 20 + 1950);
//                        int endYear = 1900 + new Date().getYear();
//                        List<Integer> result = Lists.newArrayList();
//                        for (int i = startYear; i <= endYear; i++) {
//                            result.add(i);
//                        }
//                        return result;
//                    }
//                })
//                .withAttribute("data-live-search","true")
//        );
//        withConfig(new LabelConfig("# of accidents"));
//        withConfig(new SelectPickerConfig<Integer>("insured.accidents")
//                .withOptions(Lists.newArrayList(1, 2, 3, 4, 5))
//                .withAttribute("data-live-search","true")
//        );
//
//        withConfig(new LabelConfig(getDrinkQuestion()));
//        withConfig(new SelectPickerConfig<String>("insured.drinks")
//                    .withOptions(Lists.newArrayList(
//                            "coke",
//                            "sprite",
//                            "pepsi",
//                            "gingerale",
//                            "tahiti treat",
//                            "orange crush",
//                            "coconut water",
//                            "tab (seriously?)"
//                    ))
//                .withAttribute("data-live-search","true")
//        );

        with(new ButtonConfig("button.next"));
    }

    private String getDrinkQuestion() {
        return "what type of pop do you drink?";
    }
}
