package forms.impl;

import forms.config.LabelConfig;
import forms.config.TextFieldConfig;

public class FormAAnotherNewLayoutConfig extends FormANewLayoutConfig {

    public FormAAnotherNewLayoutConfig() {
        super();
        withName("formA-3 (required field)");
        withTitle("Form A (renovated again)");
        withConfig(new LabelConfig("requiredField"),0);
        withConfig(new TextFieldConfig("insured.cc").required(),1);
    }

}
