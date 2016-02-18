package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.widgets.WfTextField;

public class TextFieldConfig<T> extends FormComponentConfig<WfTextField<T>> {

    public TextFieldConfig(String property) {
        super(property, WidgetTypeEnum.TEXT_FIELD);
    }

    @Override
    public WfTextField create(String id) {
        // TODO : this should be automated. if widgetTypeEnum knows class of widget.
        return new WfTextField<T>(id, this);
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
