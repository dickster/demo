package forms.config;

import com.google.common.collect.Lists;

public class FormCConfig extends FormConfig {

    public FormCConfig() {
        super("FORM-C");
        withTitle("Form C");
        withRenderBodyOnly(true);
        addConfigs();
    }

    private void  addConfigs() {
        with(new LabelConfig("two selectpickers with different configurations"));

        with(new SelectPickerConfig("insured.dwelling.roofType")
                .withChoices(Lists.newArrayList("shingles", "metal", "tin", "tile", "slate"))
                .withAttribute("title", "my title")
                .withAttribute("multiple", "")
                .withAttribute("data-selected-text-format","count>2")
                .withOption("header", "this is a custom header"));

        with(new SelectPickerConfig("insured.dwelling.roofType")
                .withChoices(Lists.newArrayList("shingles", "metal", "tin", "tile", "slate"))
                .withAttribute("data-live-search","true"));

        with(new LabelConfig("do you have a pool"));
        with(new TextFieldConfig("insured.dwelling.pool"));
        with(new LabelConfig("what kind of roof do you have?"));
        with(new ButtonConfig("ok"));
    }
}
