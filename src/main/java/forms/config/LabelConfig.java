package forms.config;

import forms.WidgetTypeEnum;

public class LabelConfig extends WidgetConfig {

    private String text;  // this should be localized!
    private String associatedWidget;

    public LabelConfig(String name) {
        super(name, WidgetTypeEnum.LABEL);
        text(name);
        withCss("");
    }

    public LabelConfig text(String value) {
        this.text = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public LabelConfig forAssociatedWidget(String associatedWidgetName) {
        associatedWidget = associatedWidgetName;
        return this;
    }

    public LabelConfig forAssociatedWidget(WidgetConfig config) {
        associatedWidget = config.getName();
        return this;
    }

    public String getAssociatedWidget() {
        return associatedWidget;
    }
}
