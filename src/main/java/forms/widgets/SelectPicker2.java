package forms.widgets;

import demo.resources.Resource;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.SelectPickerConfig;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;


public class SelectPicker2<T> extends FormComponentPanel<T> implements HasConfig {

    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.min.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");

    private Config config;
    private final SelectPicker select;

    public SelectPicker2(String id, SelectPickerConfig config) {
        super(id);

        // TODO : refactor this...make internal class.
        add(select = new SelectPicker("select", config));

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
        select.setModel(getParentModel());
    }

    // refactor this into class = FormComponentDelegateModel() or whatever you want to call it.
    // this same code will be used by all FormComponentPanel extending classes.
    private IModel<T> getParentModel() {

        return new IModel<T>() {
            @Override
            public T getObject() {
                return SelectPicker2.this.getModel().getObject();
            }

            @Override
            public void setObject(T value) {
                SelectPicker2.this.getModel().setObject(value);
            }

            @Override
            public void detach() {
                SelectPicker2.this.getModel().detach();
            }
        };
    }
}
