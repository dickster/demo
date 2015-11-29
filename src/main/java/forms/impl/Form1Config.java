package forms.impl;

import forms.config.ButtonConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;

public class Form1Config extends FormConfig {

    public static final String FORM_CONFIG_1 = "FORM-1";

    public Form1Config() {
        super(FORM_CONFIG_1);
        addConfigs();
        withTitle("Form 1");
    }

    private void addConfigs() {
        with(new LabelConfig("hello"));
        with(new ButtonConfig("next"));
//        DivConfig contents = new DivConfig("contents");
//
//        contents.withConfig(new LabelConfig("first name"))
//                // note how conig is relative to "name" field.
//            .withConfig(new TextFieldConfig("first"));
//
//        with(new SectionPanelConfig("names", contents)
//            .withTitle("Insured"));
//        with(new ButtonConfig("next"));
    }
}
