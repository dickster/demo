package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import demo.resources.Resource;
import forms.config.FormConfig;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.renderStrategy.DeepChildFirstVisitor;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.visit.IVisit;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

public class WorkflowForm extends Panel {

    // this calls layout and initializes all widgets.
    private static final String INIT_FORM = "workflow.init(%s);";
    private static final JavaScriptResourceReference WORKFLOW_JS = new JavaScriptResourceReference(Resource.class, "workflow.js");

    private @Inject WfUtil wfUtil;
    private @Inject Toolkit toolkit;

    private Component visitorKludge;

    private Form form;
    private List<WfAjaxHandler> ajaxHandlers = Lists.newArrayList();
    private final FeedbackPanel feedback;
    private FormConfig formConfig;

    public WorkflowForm(@Nonnull String id, @Nonnull FormConfig config, @Nonnull CompoundPropertyModel model) {
        super(id, model);
        withConfig(config);
        setOutputMarkupId(true);
        // placeholder to be replaced based on formConfig.
        add(new WebMarkupContainer("form").add(new WebMarkupContainer("content")));
        add(feedback = new FeedbackPanel("feedback"));
        feedback.setOutputMarkupPlaceholderTag(true);
        add(new Label("subheader", config.getTitle()));
    }

    public void handleError(WfSubmitErrorEvent event) {
        event.getTarget().add(feedback);
    }

    public WorkflowForm withConfig(FormConfig config) {
        this.formConfig = config;
        return this;
    }

    public WorkflowForm withFormValidator(IFormValidator validator) {
        form.add(validator);
        return this;
    }

    @Override
    protected void onInitialize() {
        Preconditions.checkNotNull(formConfig);
        super.onInitialize();
        update(formConfig);

        getTheme().apply(form);
    }

    private void update(FormConfig formConfig) {
        form = new Form("form");
        form.setOutputMarkupId(true);
        form.add(new Group("content", formConfig));
        addOrReplace(form);
    }

    public void update(FormConfig formConfig, AjaxRequestTarget target) {
        update(formConfig);
        target.add(form);
    }

    private Theme getTheme() {
        return toolkit.getTheme();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        for (HeaderItem item:getTheme().getHeaderItems()) {
            response.render(item);
        }
        response.render(JavaScriptReferenceHeaderItem.forReference(WORKFLOW_JS));
        String optionsJson = new Gson().toJson(new FormData(this));
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_FORM, optionsJson)));
    }

    public Form getForm() {
        return form;
    }

    public FormConfig getFormConfig() {
        return formConfig;
    }

    public Component getComponent(final String name) {
        // argh...because the visitor doesn't support generics we have to work around getting a return value.
        // suggest : make a copy that supports generics = EZDeepChildFirstVisitor<Component, Component>();
        visitorKludge = null;   // unfortunately, can't use a local var for this...boo!
        visitChildren(Component.class, new DeepChildFirstVisitor() {
            @Override
            public void component(Component component, IVisit<Void> visit) {
                String n = wfUtil.getComponentName(component);
                if (name.equals(n)) {
                    visitorKludge = component;
                }
            }

            @Override
            public boolean preCheck(Component component) {
                return visitorKludge!=null;
            }
        });
        return visitorKludge;
    }

}
