package forms.widgets;

import demo.resources.Resource;
import forms.model.ParentModel;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.SelectPickerConfig;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;


public class SelectPicker<T> extends FormComponentPanel<T> implements HasConfig {

    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.min.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");

    private Config config;
    private final DropDownChoice<T> dropDownChoice;

    public SelectPicker(String id, SelectPickerConfig config) {
        super(id);

        // TODO : refactor this...make internal class.
        add(dropDownChoice = new DropDownChoice("select", config.getOptionsService().getOptions()));

        setRenderBodyOnly(false);
        setOutputMarkupId(true);
        this.config = config;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        // TODO : remove this.  should be assumed included by application.
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptHeaderItem.forReference(SELECT_JS));
        response.render(CssHeaderItem.forReference(SELECT_CSS));
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        dropDownChoice.setModel(new ParentModel(this));
    }

    @Override
    protected void convertInput() {
        setConvertedInput((T) dropDownChoice.getConvertedInput());
    }

}
