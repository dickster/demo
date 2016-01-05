package forms.widgets;

import demo.resources.Resource;
import forms.widgets.config.HasConfig;
import forms.widgets.config.TextFieldConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class TextField2<T> extends TextField<T> implements HasConfig<TextFieldConfig<T>> {

    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/typeahead.bundle.js");
    private static final CssResourceReference TYPEAHEAD_CSS = new CssResourceReference(Resource.class,"bootstrap-3.1.1-dist/css/typeahead.bootstrap.css");

    private TextFieldConfig<T> config;

    public TextField2(String id, TextFieldConfig<T> config) {
        super(id);
        this.config = config;
        config.withOption("url", "http://api.themoviedb.org/3/search/movie?query=%QUERY&api_key=f22e6ce68f5e5002e71c20bcba477e7d");
        //add(new TypeAheadBehavior()/* .withUrl(config.typeaheadUrl); */);
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
        response.render(CssHeaderItem.forReference(TYPEAHEAD_CSS));
        response.render(JavaScriptHeaderItem.forReference(TYPEAHEAD_JS));
    }

    @Override
    public TextFieldConfig<T> getConfig() {
        return this.config;
    }

}
