package forms.config;

import com.google.common.collect.Lists;

import java.util.List;

public class FormBConfig extends FormConfig {

    public FormBConfig() {
        super("FORM-B");
        usingDefaultLayout(true);
        withConfigs(configs());
        withTitle("Form B");
    }

    private List<Config> configs() {
        List<Config> result = Lists.newArrayList();
        GroupConfig addressConfig = new GroupConfig("x");
        addressConfig.withConfig(new LabelConfig("Address"));
        addressConfig.withConfig(new TextFieldConfig("insured.address"));
        addressConfig.withConfig(new LabelConfig("Address2"));
        addressConfig.withConfig(new TextFieldConfig("insured.address2"));
        result.add(new GroupConfig("y").withConfigs(addressConfig));
        result.add(new ButtonConfig("next"));
        return result;
    }
}
