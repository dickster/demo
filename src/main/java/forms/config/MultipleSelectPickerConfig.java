package forms.config;

import forms.WidgetTypeEnum;
import forms.spring.SelectChoicesService;
import forms.widgets.MultipleSelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

// TODO : this should extend SelectPickerConfig????
public class MultipleSelectPickerConfig<T> extends FormComponentConfig<MultipleSelectPicker> {

    private SelectChoicesService<T> service;

    public MultipleSelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.MULTIPLE_SELECT);
        withAttribute("multiple", "");
    }

    public MultipleSelectPickerConfig withChoices(List<T> choices) {
        this.service = new SelectChoicesService<T>() {
            @Override public List<T> getChoices() {
                return service.getChoices();
            }
        };
        return this;
    }

    public MultipleSelectPickerConfig withChoices(SelectChoicesService<T> service) {
        this.service = service;
        return this;
    }

    public SelectChoicesService<T> getService() {
        return service;
    }

    @Override
    public MultipleSelectPicker create(String id) {
        return new MultipleSelectPicker(id, this);
    }

}
