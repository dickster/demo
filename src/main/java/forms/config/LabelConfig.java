package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.Label2;

import java.text.Format;

public class LabelConfig extends Config<Label2> {

    private String text = null;  // this should be localized!
    private @DontSendInJson Format format;

    public LabelConfig(String name) {
        super(name, WidgetTypeEnum.LABEL);
        text(name);
    }

    public LabelConfig(String name, boolean property) {
        super(name, WidgetTypeEnum.LABEL);
    }

    public LabelConfig(String name, Format format, String... property) {
        super(name, WidgetTypeEnum.LABEL);
        this.format = format;
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

    public Format getFormat() {
        return format;
    }
}
