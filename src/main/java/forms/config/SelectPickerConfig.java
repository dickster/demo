package forms.config;

import forms.WidgetTypeEnum;
import forms.spring.SelectChoicesService;
import forms.widgets.SelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

public class SelectPickerConfig extends FormComponentConfig<SelectPicker> {

    private List choices;


    public SelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.SELECT);
    }

    public List getChoices() {
        return choices;
    }

    public SelectPickerConfig withChoices(List<?> choices) {
        this.choices = choices;
        return this;
    }

    public SelectPickerConfig withChoices(SelectChoicesService service) {
        this.choices = service.getChoices();
        return this;
    }

    @Override
    public SelectPicker create(String id) {
        return new SelectPicker(id, this);
    }

}
