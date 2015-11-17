package forms.config;

import com.google.common.collect.Lists;

import java.util.List;

public class FormAConfig extends FormConfig {

    public FormAConfig() {
        super("FORM-A");
        withConfigs(configs());
        withTitle("Form A");
    }

    private List<Config> configs() {
        List<Config> result = Lists.newArrayList();
        GroupConfig nameConfig = new GroupConfig("names");
        nameConfig.withConfig(new LabelConfig("first name"));
        nameConfig.withConfig(new TextFieldConfig("name.first"));
        nameConfig.withConfig(new LabelConfig("middle name"));
        nameConfig.withConfig(new TextFieldConfig("name.middle"));
        nameConfig.withConfig(new LabelConfig("last name"));
        nameConfig.withConfig(new TextFieldConfig("name.last"));
        result.add(nameConfig);

        GroupConfig aiConfig = new GroupConfig("2")
        .withConfig(new LabelConfig("age"))
        .withConfig(new TextFieldConfig("insured.age"))
        .withConfig(new LabelConfig("occupation"))
        .withConfig(new TextFieldConfig("insured.occupation"));
        result.add(aiConfig);

        result.add(new ButtonConfig("next"));
        result.add(new ButtonConfig("formA3"));
        return result;
    }
}
