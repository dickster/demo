package forms.impl;

import com.google.common.collect.Lists;
import forms.widgets.config.*;

public class Template1FormConfig extends FormConfig {

    public Template1FormConfig() {
        super("template1");
        withTitle("Demo");
        addConfigs();
    }

    private void addConfigs() {
        with(new LabelConfig("question.convicted"));
        with(new YesNoConfig("insured.q1"));
    }


}
