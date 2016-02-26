package forms.widgets.config;

import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import forms.spring.SelectOptionsProvider;
import forms.widgets.SelectPicker;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.List;

public class SelectPickerConfig<T> extends FormComponentConfig<SelectPicker> {

    // TODO : should this be transient or serializable?
    // make this the name of a spring bean.
    private transient SelectOptionsProvider<T> provider;
    private boolean allowMultiple = false;

    public SelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.SELECT);
        //withWrappedHtmlOutput();
        //appendCss("form-control");
        withSelector("select");
    }

    public SelectOptionsProvider<T> getOptionsProvider() {
        return provider;
    }

    // TODO : rename this to "choices" so it's not confused with other options.
    public SelectPickerConfig withOptions(final List<T> choices) {
        this.provider = new SelectOptionsProvider() {
            @Override
            public List getOptions() {
                return choices;
            }
        };
        return this;
    }

    public SelectPickerConfig withJsOptions(String jsOptions) {
        // return empty list in wicket...   let .js provide the values.
        this.provider = new SelectOptionsProvider() {
            @Override public List getOptions() {
                return Lists.newArrayList();
            }
        };
        withOption("data", jsOptions);
        return this;
    }

    public SelectPickerConfig withTitle(String value) {
        withOption("title", value);
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

    public SelectPickerConfig allowMultiple() {
        this.allowMultiple = true;
        return this;
    }

    public boolean allowsMultiple() {
        return allowMultiple;
    }

    public SelectPickerConfig<T> allowSearch() {
        withOption("liveSearch", "true");
        return this;
    }

}
