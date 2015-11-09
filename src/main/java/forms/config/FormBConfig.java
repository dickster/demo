package forms.config;

import com.google.common.collect.Lists;

import java.util.List;

public class FormBConfig extends FormConfig {

    public FormBConfig() {
        super();
        usingDefaultLayout(true);
        withConfigs(configs());
        withName("FORM-B");
        withTitle("Form B");
        withVersion("acord-1.2.3");
    }

    private List<Config> configs() {
        List<Config> result = Lists.newArrayList();
        GroupConfig addressConfig = new GroupConfig();
        addressConfig.withConfig(new LabelConfig("Address"));
        addressConfig.withConfig(new TextFieldConfig("insured.address"));
        addressConfig.withConfig(new LabelConfig("Address2"));
        addressConfig.withConfig(new TextFieldConfig("insured.address2"));
        result.add(new GroupConfig().withConfigs(addressConfig));
        result.add(new ButtonConfig("next"));
        return result;
    }
}
