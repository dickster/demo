package forms.config;

import com.google.common.collect.Lists;

import java.util.List;

public class FormCConfig extends FormConfig {

    public FormCConfig() {
        super();
        usingDefaultLayout(true);
        withConfigs(configs());
        withName("FORM-C");
        withTitle("Form C");
        withVersion("acord-1.2.3");
    }

    private List<Config> configs() {
        List<Config> result = Lists.newArrayList();
        GroupConfig coveragesConfig = new GroupConfig();
        // dropdown = type of roof.  jchosen.
        // checkbox=do you smoke?  (YN?)
        // earthquake zone (depends on postal code/country/state)
        //
        coveragesConfig.withConfig(new LabelConfig("do you have a pool"));
        coveragesConfig.withConfig(new TextFieldConfig("insured.pool"));
//        coveragesConfig.withConfig(new LabelConfig("l1").text("middle name"));
//        coveragesConfig.withConfig(new TextFieldConfig("name.middle"));
//        coveragesConfig.withConfig(new LabelConfig("l1").text("last name"));
//        coveragesConfig.withConfig(new TextFieldConfig("name.last"));
        result.add(new GroupConfig().withConfigs(coveragesConfig));
        result.add(new ButtonConfig("ok"));
        return result;
    }
}
