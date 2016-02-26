package forms.impl;

import forms.widgets.config.AddressConfig;
import forms.widgets.config.ButtonConfig;
import forms.widgets.config.DialogConfig;
import forms.widgets.config.DivConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;
import forms.widgets.config.TextFieldConfig;

public class FormAConfig extends FormConfig {

    public static final String FORM_CONFIG_A = "FORM-A";


    public FormAConfig() {
        super(FORM_CONFIG_A);
        addConfigs();
        withTitle("Form A");
    }

    private void addConfigs() {
        with(new DivConfig("names")
            .withConfig(new LabelConfig("first name"))
            .withConfig(new TextFieldConfig("name.first"))
            .withConfig(new LabelConfig("middle name"))
            .withConfig(new TextFieldConfig("name.middle"))
            .withConfig(new LabelConfig("last name"))
            .withConfig(new TextFieldConfig("name.last")));

        with(new DivConfig("2")
                .withConfig(new LabelConfig("address"))
                .withConfig(new AddressConfig("insured.address"))
                .withConfig(new LabelConfig("age"))
                .withConfig(new TextFieldConfig("insured.age"))
                .withConfig(new LabelConfig("occupation"))
                .withConfig(new TextFieldConfig("insured.occupation")));

        with(new ButtonConfig("next"));
//        with(new ButtonConfig("formAx"));
//        with(new ButtonConfig("formAy"));
//        with(new ButtonConfig("formB"));
//        with(new ButtonConfig("formC"));
//        with(new ButtonConfig("formError"));

        // how to make model of dialog that references current sectionpanel model?
        DialogConfig dialog = (DialogConfig) new DialogConfig("dialog")
          .withSubmitButton("ok label override")
          .withCancelButton("cancel button override")
          .with(new LabelConfig("label.email"))
          .with(new LabelConfig("label.phone"));
        //  .withSubmittingButton("label")

//        with(new ButtonConfig("hello").forDialog(dialog));
        //buttonConfig().forDialog(d);

//        dialogConfig
//                .withButtons(new DialogSubmitButtonConfig("ok"),
//                        new DialogSubmitButtonConfig("cancel"),
//                        new DialogSubmitButtonConfig("save"))
//                .withTitle("Hello!")
//                .withConfigs(new LabelConfig("First Name").name("fnlbl"),
//                        new TextFieldConfig("name.first").name("fname"),
//                        new LabelConfig("Last Name").name("lnlbl"),
//                        new TextFieldConfig("name.last").name("lname"));
//        with(dialogConfig);
//        with(dialogConfig.createInvokingButtonConfig("test"));
    }
}
