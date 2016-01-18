package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.spring.SelectOptionsProvider;
import forms.widgets.MultipleSelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

// TODO : this should extend SelectPickerConfig????
public class MultipleSelectPickerConfig<T> extends FormComponentConfig<MultipleSelectPicker> {

    private SelectOptionsProvider<T> service;

    public MultipleSelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.MULTIPLE_SELECT);
        withAttribute("multiple", "");
    }

    public MultipleSelectPickerConfig withChoices(List<T> choices) {
        this.service = new SelectOptionsProvider<T>() {
            @Override public List<T> getOptions() {
                return service.getOptions();
            }
        };
        return this;
    }

    public MultipleSelectPickerConfig withChoices(SelectOptionsProvider<T> service) {
        this.service = service;
        return this;
    }

    public SelectOptionsProvider<T> getService() {
        return service;
    }

    @Override
    public MultipleSelectPicker create(String id) {
        return new MultipleSelectPicker(id, this);
    }

}
