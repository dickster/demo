package forms.impl;

import com.google.common.collect.Lists;
import forms.widgets.config.ButtonConfig;
import forms.widgets.config.CheckBoxConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;
import forms.widgets.config.SelectPickerConfig;

public class FormCConfig extends FormConfig {

    public FormCConfig() {
        super("FORM-C");
        withTitle("Form C");
        addConfigs();
    }

    private void  addConfigs() {
        with(new LabelConfig("(two selectpickers with different configurations)"));
        with(new LabelConfig("what kind of roof do you have?"));
        with(new SelectPickerConfig("insured.dwelling.roofType")
                .withOptions(Lists.newArrayList("shingles", "metal", "tin", "tile", "slate"))
                .withAttribute("title", "my title")
                .withAttribute("multiple", "")
                .withAttribute("data-selected-text-format","count>2")
                .withOption("header", "this is a custom header"));

        with(new SelectPickerConfig("insured.dwelling.roofType")
                .withOptions(Lists.newArrayList("shingles", "metal", "tin", "tile", "slate"))
                .withAttribute("data-live-search","true"));


        with(new CheckBoxConfig("insured.dwelling.pool", "I have an outdoor pool."));
        with(new ButtonConfig("ok"));
    }
}
