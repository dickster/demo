package forms.widgets;

import com.google.gson.Gson;
import demo.resources.Resource;
import forms.widgets.config.SelectConfig;
import org.apache.wicket.Application;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.Map;

public class BootstrapSelectPicker<T> extends DropDownChoice<T> {

    private static final String INIT = "easy.selectPicker.init('%s',%s)";

    private static final JavaScriptResourceReference JS = new JavaScriptResourceReference(Resource.class, "selectPicker.js");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.min.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");

    private SelectConfig config;

    public BootstrapSelectPicker(String id, SelectConfig config) {
        super(id, config.getChoicesProvider().getChoices());
        setOutputMarkupId(true);
        this.config = config;
        setNullValid(false);
    }

    public BootstrapSelectPicker withModel(IModel<T> model) {
        setDefaultModel(model);
        return this;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setNullValid(!config.allowsMultiple());
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        Map<String,String> attrs = config.getAttributes();
        for (String attr: attrs.keySet()) {
            tag.getAttributes().put(attr, attrs.get(attr) );
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptHeaderItem.forReference(SELECT_JS));
        response.render(JavaScriptHeaderItem.forReference(JS));
        response.render(CssHeaderItem.forReference(SELECT_CSS));
        String options = new Gson().toJson(config.getOptions());
        String x = String.format(INIT, getMarkupId(), options);
        response.render(OnDomReadyHeaderItem.forScript(x));
    }

}
