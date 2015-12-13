package forms.config;

import forms.WidgetTypeEnum;
import forms.spring.SelectChoicesService;
import forms.widgets.SelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

public class SelectPickerConfig extends FormComponentConfig<SelectPicker> {

    // TODO : should this be transient or serializable???
    private transient SelectChoicesService service;


    public SelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.SELECT);
    }

    public SelectChoicesService getChoicesService() {
        return service;
    }

    public SelectPickerConfig withChoices(final List<?> choices) {
        this.service = new SelectChoicesService() {
            @Override
            public List getChoices() {
                return choices;
            }
        };
        return this;
    }

    public SelectPickerConfig withChoices(SelectChoicesService service) {
        this.service = service;
        return this;
    }

    @Override
    public SelectPicker create(String id) {
        return new SelectPicker(id, this);
    }

}
