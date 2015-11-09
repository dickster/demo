package forms.config;

import com.google.common.collect.Lists;

import java.util.List;

public class FormAConfig extends FormConfig {

    public FormAConfig() {
        super();
        usingDefaultLayout(true);
        withConfigs(configs());
        withName("FORM-A");
        withTitle("Form A");
        withVersion("acord-1.2.3");
    }

    private List<Config> configs() {
        List<Config> result = Lists.newArrayList();
        GroupConfig nameConfig = new GroupConfig();
        nameConfig.withConfig(new LabelConfig("first name"));
        nameConfig.withConfig(new TextFieldConfig("name.first"));
        nameConfig.withConfig(new LabelConfig("middle name"));
        nameConfig.withConfig(new TextFieldConfig("name.middle"));
        nameConfig.withConfig(new LabelConfig("last name"));
        nameConfig.withConfig(new TextFieldConfig("name.last"));
        result.add(new GroupConfig().withConfigs(nameConfig));
        result.add(new ButtonConfig("next"));
        return result;
    }
}
