package forms.widgets;

import demo.resources.Resource;
import forms.widgets.config.HasConfig;
import forms.widgets.config.TextFieldConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class TextField2<T> extends TextField<T> implements HasConfig<TextFieldConfig<T>> {

    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-typeahead.js");
    private static final JavaScriptResourceReference BLOODHOUND_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/Bloodhound.js");

    private TextFieldConfig<T> config;

    public TextField2(String id, TextFieldConfig<T> config) {
        super(id);
        this.config = config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "text");
        super.onComponentTag(tag);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(TYPEAHEAD_JS));
        response.render(JavaScriptHeaderItem.forReference(BLOODHOUND_JS));
    }

    @Override
    public TextFieldConfig<T> getConfig() {
        return this.config;
    }

}
