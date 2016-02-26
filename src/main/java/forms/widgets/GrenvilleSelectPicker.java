package forms.widgets;

import com.google.gson.Gson;
import demo.resources.Resource;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.SelectPickerConfig;
import org.apache.wicket.Application;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;


public class GrenvilleSelectPicker<T> extends DropDownChoice<T> implements HasConfig {

    private static final String INIT = "easy.selectPicker.init('%s',%s)";

    private static final JavaScriptResourceReference JS = new JavaScriptResourceReference(Resource.class, "selectPicker.js");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.min.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");

    private SelectPickerConfig config;

    public GrenvilleSelectPicker(String id, SelectPickerConfig config) {
        super(id, config.getOptionsProvider().getOptions());
        setOutputMarkupId(true);
        this.config = config;
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        if (config.allowsMultiple()) {
            tag.getAttributes().put("multiple","");
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

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setNullValid(!config.allowsMultiple());
    }

//    protected boolean isDisabled(final T object, int index, String selected) {
//       return false;// return index==0 && config.hasTitle();
//    }

//    @Override
//    protected void setOptionAttributes(AppendingStringBuffer buffer, T choice, int index, String selected) {
//        if (index==0 && config.hasTitle()) {
//            buffer.append(" hidden ");
//        }
//        super.setOptionAttributes(buffer, choice, index, selected);
//    }
}
