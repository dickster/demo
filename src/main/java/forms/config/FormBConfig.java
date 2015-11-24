package forms.config;

import org.apache.wicket.validation.validator.EmailAddressValidator;

public class FormBConfig extends FormConfig {

    public FormBConfig() {
        super("FORM-B");
        addConfigs();
        withTitle("Form B");
    }

    private void addConfigs() {
        with( new GrpConfig("deets")
                .withConfig(new LabelConfig("Phone"))
                .withConfig(new TextFieldConfig("insured.phone").withPrefix("(647)"))
                .withConfig(new LabelConfig("Deductible"))
                .withConfig(new TextFieldConfig("insured.deductible").withPrefix("$").withSuffix(".00"))
                .withConfig(new LabelConfig("Email"))
                .withConfig(new TextFieldConfig("insured.email")
                                .required()
                                .addValidator(EmailAddressValidator.getInstance()))
                .withConfig(new CheckBoxConfig("insured.notifyMe", "email me when policy is about to expire.")));
        with(new ButtonConfig("next"));
    }

}
