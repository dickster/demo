package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.widgets.TextField2;

public class TextFieldConfig<T> extends FormComponentConfig<TextField2<T>> {

    public TextFieldConfig(String property) {
        super(property, WidgetTypeEnum.TYPEAHEAD);
    }

    @Override
    public TextField2 create(String id) {
        // TODO : this should be automated. if widgetTypeEnum knows class of widget.
        return new TextField2<T>(id, this);
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
