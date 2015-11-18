package forms.config;

import com.google.common.collect.Lists;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import java.util.List;

public class FormBConfig extends FormConfig {

    public FormBConfig() {
        super("FORM-B");
        withConfigs(configs());
        withTitle("Form B");
    }

    private List<Config> configs() {
        List<Config> result = Lists.newArrayList();
        GroupConfig addressConfig = new GroupConfig("x");
        addressConfig.withConfig(new LabelConfig("Address").required(true));
        addressConfig.withConfig(new TextFieldConfig("insured.address"));
        addressConfig.withConfig(new LabelConfig("Email"));
        addressConfig.withConfig(new TextFieldConfig("insured.email")
                .addValidator(EmailAddressValidator.getInstance()));
        result.add(new GroupConfig("y").withConfigs(addressConfig));
        result.add(new ButtonConfig("next"));
        return result;
    }
}
