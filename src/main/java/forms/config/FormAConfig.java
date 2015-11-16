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
    }

    private List<Config> configs() {
        List<Config> result = Lists.newArrayList();
        GroupConfig nameConfig = new GroupConfig("1");
        nameConfig.withConfig(new LabelConfig("first name"));
        nameConfig.withConfig(new TextFieldConfig("name.first"));
        nameConfig.withConfig(new LabelConfig("middle name"));
        nameConfig.withConfig(new TextFieldConfig("name.middle"));
        nameConfig.withConfig(new LabelConfig("last name"));
        nameConfig.withConfig(new TextFieldConfig("name.last"));
        result.add(new GroupConfig("1").withConfigs(nameConfig));
        result.add(new ButtonConfig("next"));
        return result;
    }
}
