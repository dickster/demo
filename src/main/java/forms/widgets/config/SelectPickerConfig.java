package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.spring.SelectOptionsProvider;
import forms.widgets.SelectPicker;

import javax.annotation.Nonnull;
import java.util.List;

public class SelectPickerConfig<T> extends FormComponentConfig<SelectPicker> {

    // TODO : should this be transient or serializable?
    // make this the name of a spring bean.
    private transient SelectOptionsProvider<T> provider;

    public SelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.SELECT);
        //withWrappedHtmlOutput();
        //appendCss("form-control");
        withSelector("select");
    }

    public SelectOptionsProvider<T> getOptionsService() {
        return provider;
    }

    public SelectPickerConfig withOptions(final List<T> choices) {
        this.provider = new SelectOptionsProvider() {
            @Override
            public List getOptions() {
                return choices;
            }
        };
        return this;
    }

    public SelectPickerConfig withOptions(SelectOptionsProvider<T> service) {
        this.provider = service;
        return this;
    }

    // NOTE : you could just as easily include this in the template. that would be lighter weight
    // that the java approach but much more difficult to migrate/fix/debug etc...
    // e.g. template = <select data-tmpl="foobar" data-live-search="true"/>
    public SelectPickerConfig withSearchingAllowed() {
        withAttribute("data-live-search","true");
        return this;
    }


    @Override
    public SelectPicker<T> create(String id) {
        return new SelectPicker<T>(id, this);
    }

}
