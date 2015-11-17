package forms;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import demo.resources.Resource;
import forms.config.FormConfig;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class WorkflowForm extends Panel {

    // this calls layout and initializes all widgets.
    // TODO : use gridstack to handle layouts?
    private static final String INIT_FORM = "easy.layout.init(%s);";
    private static final JavaScriptResourceReference LAYOUT_JS = new JavaScriptResourceReference(Resource.class, "layout.js");
    private final Form form;

    private @Inject Toolkit toolkit;

    private FormConfig formConfig;
    private CompoundPropertyModel<?> formModel;
    private String expectedAcordVersion; // TODO : set valid default here...

    public WorkflowForm(@Nonnull String id, @Nonnull FormConfig config, @Nonnull CompoundPropertyModel model) {
        super(id);
        withConfig(config);
        WfUtil.setComponentName(this, config.getName());
        setOutputMarkupId(true);
        add(form = new Form("form") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                form.visitChildren(Component.class, new IVisitor<Component, Object>() {
                    @Override
                    public void component(Component component, IVisit<Object> visit) {
                        Object o = component.getDefaultModelObject();
                        System.out.println(o);
                    }
                });
            }
        });
        form.setModel(model);
        Object x = form.getModelObject();
        add(new Label("subheader", config.getTitle()));
    }

    public WorkflowForm withConfig(FormConfig config) {
        this.formConfig = config;
        return this;
    }

    public WorkflowForm withModel(CompoundPropertyModel<?> model) {
        this.formModel = model;
        return this;
    }

    public WorkflowForm withFormValidator(IFormValidator validator) {
        form.add(validator);
        return this;
    }

    public CompoundPropertyModel<?> getFormModel() {
        return (CompoundPropertyModel<?>) form.getModel();
    }

    @Override
    protected void onInitialize() {
        Preconditions.checkNotNull(formConfig);

        super.onInitialize();

        form.setOutputMarkupId(true);

        form.add(new Group("content", formConfig));

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
        String optionsJson = new Gson().toJson(getFormJsonOptions());
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_FORM, optionsJson)));
    }

    private FormJsonOptions getFormJsonOptions() {
        return new FormJsonOptions(this);
    }

    public FormConfig getFormConfig() {
        return formConfig;
    }


}