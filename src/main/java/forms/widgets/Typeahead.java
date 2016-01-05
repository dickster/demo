package forms.widgets;

import forms.ajax.TypeAheadBehavior;
import forms.widgets.config.TextFieldConfig;

public class Typeahead<T> extends TextField2<T> {

    private TextFieldConfig<T> config;
    //need to use custom config??

    public Typeahead(String id, TextFieldConfig<T> config) {
        super(id, config);
        add(new TypeAheadBehavior());
    }


}
