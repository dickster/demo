package forms.impl;

import com.google.common.collect.Lists;
import forms.config.ButtonConfig;
import forms.config.CheckBoxConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;
import forms.config.SelectPickerConfig;

public class FormCConfig extends FormConfig {

    public FormCConfig() {
        super("FORM-C");
        withTitle("Form C");
        withRenderBodyOnly(true);
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
