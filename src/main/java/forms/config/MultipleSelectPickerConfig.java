package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.MultipleSelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

public class MultipleSelectPickerConfig extends FormComponentConfig<MultipleSelectPicker> {

    private List choices;


    public MultipleSelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.MULTIPLE_SELECT);
        withAttribute("multiple", "");
    }

    public List getChoices() {
        return choices;
    }

    public MultipleSelectPickerConfig withChoices(List<?> choices) {
        this.choices = choices;
        return this;
    }

    @Override
    public MultipleSelectPicker create(String id) {
        return new MultipleSelectPicker(id, this);
    }

}
