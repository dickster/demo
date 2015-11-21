package forms.config;

import org.apache.wicket.validation.validator.EmailAddressValidator;

public class FormBConfig extends FormConfig {

    public FormBConfig() {
        super("FORM-B");
        addConfigs();
        withTitle("Form B");
    }

    private void addConfigs() {
        with( new GroupConfig("address")
                .withConfig(new LabelConfig("Address").required(true))
                .withConfig(new TextFieldConfig("insured.address"))
                .withConfig(new LabelConfig("Email"))
                .withConfig(new TextFieldConfig("insured.email")
                                .required()
                                .addValidator(EmailAddressValidator.getInstance())));
        with(new ButtonConfig("next"));
    }
}
