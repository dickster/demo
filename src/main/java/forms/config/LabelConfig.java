package forms.config;

import forms.WidgetTypeEnum;

public class LabelConfig extends WidgetConfig {

    private String text;  // this should be localized.

    public LabelConfig(String name) {
        super(name, WidgetTypeEnum.LABEL);
        text(name);
    }

    public LabelConfig text(String value) {
        this.text = value;
        return this;
    }

    public String getText() {
        return text;
    }
}
