package forms.config;

import com.google.common.collect.Lists;

import java.util.List;

public class FormCConfig extends FormConfig {

    public FormCConfig() {
        super();
        withConfigs(configs());
        withName("FORM-C");
        withTitle("Form C");
        withVersion("acord-1.2.3");
    }

    private List<Config> configs() {
        List<Config> result = Lists.newArrayList();
        GroupConfig nameConfig = new GroupConfig();
        // dropdown = type of roof.  jchosen.
        // checkbox=do you smoke?  (YN?)
        // earthquake zone (depends on postal code/country/state)
        //
        nameConfig.withConfig(new LabelConfig("do you have a pool"));
        nameConfig.withConfig(new TextFieldConfig("insured.pool"));
//        nameConfig.withConfig(new LabelConfig("l1").text("middle name"));
//        nameConfig.withConfig(new TextFieldConfig("name.middle"));
//        nameConfig.withConfig(new LabelConfig("l1").text("last name"));
//        nameConfig.withConfig(new TextFieldConfig("name.last"));
        result.add(new GroupConfig().withConfigs(nameConfig));
        result.add(new ButtonConfig("ok"));
        return result;
    }
}
