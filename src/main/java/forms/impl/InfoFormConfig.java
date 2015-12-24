package forms.impl;

import forms.config.AddressConfig;
import forms.config.ButtonConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;
import forms.config.TextFieldConfig;

public class InfoFormConfig extends FormConfig {

    public InfoFormConfig() {
        super("INFO_FORM");
        addConfigs();
        withTitle("Client Information");
    }

    private void addConfigs() {
//        withConfig(new LabelConfig(".").name("spacer"));
//        withConfig(new SelectPickerConfig<String>("name.salutation")
//                .withOptions(Lists.newArrayList("Mr.", "Mrs.", "Ms", "Dr.")));

        withConfig(new LabelConfig("first name"));
        withConfig(new TextFieldConfig("name.first"));
        withConfig(new LabelConfig("middle name"));
        withConfig(new TextFieldConfig("name.middle"));
        withConfig(new LabelConfig("last name"));
        withConfig(new TextFieldConfig("name.last"));
        withConfig(new LabelConfig("age"));
        withConfig(new TextFieldConfig<Integer>("insured.age").withAjaxBehavior("ageOccupationAjaxBehavior"));
        withConfig(new LabelConfig("occupation"));
        withConfig(new TextFieldConfig<Integer>("insured.occupation"));


//        withConfig(new CheckBoxConfig("insured.smokes", "i smoke cigarettes"));

        withConfig(new LabelConfig("address"));
        withConfig(new AddressConfig("insured.address"));

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

        with(new ButtonConfig("next"));
    }

    private String getDrinkQuestion() {
        return "what type of pop do you drink?";
    }
}
