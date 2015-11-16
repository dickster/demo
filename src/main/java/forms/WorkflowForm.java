package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import demo.resources.Resource;
import forms.config.FormConfig;
import forms.config.PageLayout;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;

public class WorkflowForm extends Panel {

    // this calls layout and initializes all widgets.
    // TODO : use gridstack to handle layouts?
    private static final String INIT_FORM = "easy.layout.init(%s);";
    private static final JavaScriptResourceReference LAYOUT_JS = new JavaScriptResourceReference(Resource.class, "layout.js");
    private final Form form;

    private @Inject Toolkit toolkit;

    private FormConfig formConfig;
    private IModel<?> formModel;
    private String expectedAcordVersion; // TODO : set valid default here...

    public WorkflowForm(String id, FormConfig config) {
        super(id);
        withConfig(config);
        setOutputMarkupId(true);
        add(form = new Form("form"));
        add(new Label("subheader", config.getTitle()));
    }

    public WorkflowForm withConfig(FormConfig config) {
        this.formConfig = config;
        return this;
    }

    public WorkflowForm withModel(IModel<?> model) {
        this.formModel = model;
        return this;
    }

    public WorkflowForm withFormValidator(IFormValidator validator) {
        form.add(validator);
        return this;
    }

    public IModel<?> getFormModel() {
        return formModel;
    }

    @Override
    protected void onInitialize() {
        Preconditions.checkNotNull(formConfig);
        Preconditions.checkState(getFormModel() != null && getFormModel() instanceof CompoundPropertyModel);

        super.onInitialize();

        form.setOutputMarkupId(true);
        form.setDefaultModel(getFormModel());

        form.add(new Group("content", formConfig, getFormModel()));

        getTheme().apply(form);
    }

    private JsonOptions getOptions(Component widget) {
        JsonOptions options = ((HasJsonOptions) widget).getOptions();
        return options;
    }

    private Theme getTheme() {
        return toolkit.getTheme();
    }

    public <T extends WorkflowForm> T supportingAcordVersion(String version) {
        this.expectedAcordVersion = version;
        return (T) this;
    }

       @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        for (HeaderItem item:getTheme().getHeaderItems()) {
            response.render(item);
        }
        response.render(JavaScriptReferenceHeaderItem.forReference(LAYOUT_JS));
        String optionsJson = new Gson().toJson(getFormOptions());
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_FORM, optionsJson)));
    }

    private FormJsonOptions getFormOptions() {
        final FormJsonOptions formOptions = new FormJsonOptions(this, getFormConfig());
        return formOptions;
    }

    public FormConfig getFormConfig() {
        return formConfig;
    }


}
