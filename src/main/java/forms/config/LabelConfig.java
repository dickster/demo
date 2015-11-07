package forms.config;

import forms.WidgetTypeEnum;

public class LabelConfig extends WidgetConfig {


    private String text;

    public LabelConfig(String name) {
        super("", name, WidgetTypeEnum.LABEL);
    }

    public LabelConfig text(String value) {
        this.text = value;
        return this;
    }

}
