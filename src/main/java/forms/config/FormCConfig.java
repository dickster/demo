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
        with(new SelectPickerConfig("insured.dwelling.roofType")
                .withChoices(Lists.newArrayList("shingles", "metal", "tile", "slate")));
        with(new LabelConfig("do you have a pool"));
        with(new TextFieldConfig("insured.dwelling.pool"));
        with(new LabelConfig("what kind of roof do you have?"));
        with(new ButtonConfig("ok"));
    }
}
