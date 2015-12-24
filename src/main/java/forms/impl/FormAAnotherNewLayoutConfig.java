package forms.impl;

import forms.widgets.config.LabelConfig;
import forms.widgets.config.TextFieldConfig;

public class FormAAnotherNewLayoutConfig extends FormANewLayoutConfig {

    public FormAAnotherNewLayoutConfig() {
        super();
        withId("formA-3 (required field)");
        withTitle("Form A (renovated again)");
        withConfig(new LabelConfig("requiredField"),0);
        withConfig(new TextFieldConfig("insured.cc").required(),1);
    }

}
