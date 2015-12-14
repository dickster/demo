package forms.config;

import forms.WidgetTypeEnum;
import forms.spring.SelectOptionsService;
import forms.widgets.SelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

public class SelectPickerConfig<T> extends FormComponentConfig<SelectPicker> {

    // TODO : should this be transient or serializable???
    private transient SelectOptionsService<T> service;


    public SelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.SELECT);
        withWrappedHtmlOutput();
    }

    public SelectOptionsService<T> getOptionsService() {
        return service;
    }

    public SelectPickerConfig withOptions(final List<T> choices) {
        this.service = new SelectOptionsService() {
            @Override
            public List getOptions() {
                return choices;
            }
        };
        return this;
    }

    public SelectPickerConfig withOptions(SelectOptionsService<T> service) {
        this.service = service;
        return this;
    }

    @Override
    public SelectPicker<T> create(String id) {
        return new SelectPicker<T>(id, this);
    }

}
