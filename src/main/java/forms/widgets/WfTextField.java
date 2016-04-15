package forms.widgets;

import demo.resources.Resource;
import forms.widgets.config.HasConfig;
import forms.widgets.config.TextFieldConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class WfTextField<T> extends TextField<T> implements HasConfig<TextFieldConfig<T>> {

    private TextFieldConfig<T> config;

    public WfTextField(String id, TextFieldConfig<T> config) {
        super(id);
        this.config = config;
    }

    @Override
    public TextFieldConfig<T> getConfig() {
        return this.config;
    }

}
