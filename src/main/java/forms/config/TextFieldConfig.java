package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.TextField2;

public class TextFieldConfig extends FormComponentConfig<TextField2> {

    public TextFieldConfig(String property) {
        super(property, WidgetTypeEnum.TEXT_FIELD);
    }

    @Override
    public TextField2 create(String id) {
        return new TextField2(id, this);
    }

    public TextFieldConfig withPrefix(String prefix) {
        withOption("prefix", prefix);
        return this;
    }

    public TextFieldConfig withSuffix(String suffix) {
        withOption("suffix", suffix);
        return this;
    }

}
