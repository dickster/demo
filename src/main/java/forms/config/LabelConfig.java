package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.Label2;

public class LabelConfig extends Config<Label2> {

    private String text;  // this should be localized!

    public LabelConfig(String name) {
        super(name, WidgetTypeEnum.LABEL);
        text(name);
        withCss("");
    }

    public LabelConfig(String name, boolean property) {
        super(name, WidgetTypeEnum.LABEL);
        withCss("");
    }

    public LabelConfig(String name, String format, String... property) {
        super(name, WidgetTypeEnum.LABEL);
        withCss("");
    }

    public LabelConfig text(String value) {
        this.text = value;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public Label2 create(String id) {
        return new Label2(id, this);
    }

}
