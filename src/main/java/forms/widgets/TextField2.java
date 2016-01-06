package forms.widgets;

import demo.resources.Resource;
import forms.ajax.TypeAheadBehavior;
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
        // only if config has typeahead behavior spring bean.  // could be a resource too?
        // in that case, just pass the url, don't need a spring bean? resource.getUrl();
        // implements the IJsonResource interface.
//        config.withOption("url", "http://api.themoviedb.org/3/search/movie?query=%QUERY&api_key=f22e6ce68f5e5002e71c20bcba477e7d");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        TypeAheadBehavior behavior = new TypeAheadBehavior();
        add(behavior);
        config.withOption("url", behavior.getCallbackUrl().toString());
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
//        response.render(CssHeaderItem.forReference(TYPEAHEAD_CSS));
        response.render(JavaScriptHeaderItem.forReference(TYPEAHEAD_JS));
        response.render(JavaScriptHeaderItem.forReference(BLOODHOUND_JS));
    }

    @Override
    public TextFieldConfig<T> getConfig() {
        return this.config;
    }

}
