package forms.config;

import forms.WidgetTypeEnum;
import forms.spring.SelectChoicesService;
import forms.widgets.SelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

public class SelectPickerConfig<T> extends FormComponentConfig<SelectPicker> {

    // TODO : should this be transient or serializable???
    private transient SelectChoicesService<T> service;


    public SelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.SELECT);
    }

    public SelectChoicesService<T> getChoicesService() {
        return service;
    }

    public SelectPickerConfig withChoices(final List<T> choices) {
        this.service = new SelectChoicesService() {
            @Override
            public List getChoices() {
                return choices;
            }
        };
        return this;
    }

    public SelectPickerConfig withChoices(SelectChoicesService<T> service) {
        this.service = service;
        return this;
    }

    @Override
    public SelectPicker<T> create(String id) {
        return new SelectPicker<T>(id, this);
    }

}
