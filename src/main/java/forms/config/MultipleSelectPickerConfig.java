package forms.config;

import forms.WidgetTypeEnum;
import forms.spring.SelectOptionsService;
import forms.widgets.MultipleSelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

// TODO : this should extend SelectPickerConfig????
public class MultipleSelectPickerConfig<T> extends FormComponentConfig<MultipleSelectPicker> {

    private SelectOptionsService<T> service;

    public MultipleSelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.MULTIPLE_SELECT);
        withAttribute("multiple", "");
    }

    public MultipleSelectPickerConfig withChoices(List<T> choices) {
        this.service = new SelectOptionsService<T>() {
            @Override public List<T> getOptions() {
                return service.getOptions();
            }
        };
        return this;
    }

    public MultipleSelectPickerConfig withChoices(SelectOptionsService<T> service) {
        this.service = service;
        return this;
    }

    public SelectOptionsService<T> getService() {
        return service;
    }

    @Override
    public MultipleSelectPicker create(String id) {
        return new MultipleSelectPicker(id, this);
    }

}
