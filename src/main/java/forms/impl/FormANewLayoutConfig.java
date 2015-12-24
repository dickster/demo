package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;
import forms.widgets.config.TextFieldConfig;

public class FormANewLayoutConfig extends FormConfig {

    public FormANewLayoutConfig() {
        super("FORM-A-relayout");
        withTitle("Form A (renovated)");
        addConfigs();
    }

    private void addConfigs() {
        with(new LabelConfig("first name"));
        with(new TextFieldConfig("name.first"));
        with(new LabelConfig("middle name"));
        with(new TextFieldConfig("name.middle"));
        with(new LabelConfig("last name"));
        with(new TextFieldConfig("name.last"));
        with(new LabelConfig("age"));
        with(new TextFieldConfig("insured.age"));
        with(new LabelConfig("occupation"));
        with(new TextFieldConfig("insured.occupation"));
        with(new ButtonConfig("next"));
    }
}
