package forms;

import com.google.common.base.Preconditions;
import demo.resources.Resource;
import forms.config.Config;
import forms.config.FormConfig;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.renderStrategy.DeepChildFirstVisitor;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.visit.IVisit;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class WorkflowForm extends Panel implements HasConfig {

    // this calls layout and initializes all widgets.
    private static final JavaScriptResourceReference WORKFLOW_JS = new JavaScriptResourceReference(Resource.class, "workflow.js");

    private @Inject WfUtil wfUtil;
    private @Inject Toolkit toolkit;

    private Component visitorKludge;

    private Form form;
    private final FeedbackPanel feedback;
    private FormConfig formConfig;
    private AbstractDefaultAjaxBehavior historyMaker;

    public WorkflowForm(@Nonnull String id, @Nonnull FormConfig config) {
        super(id);
        withConfig(config);
        setOutputMarkupId(true);

        setupHistory();
        // placeholder to be replaced based on formConfig.
        add(new WebMarkupContainer("form").add(new WebMarkupContainer("content")));
        add(feedback = new FeedbackPanel("feedback"));
        feedback.setOutputMarkupPlaceholderTag(true);
        add(new Label("subheader", config.getTitle()));
    }

    private void setupHistory() {
        // any stuff needed for html 5 history/ajax/back button related code.


        Model<String> model = new Model<String>() {
            // model just for debugging until i'm sure it's never being set.
            // then replace it with std property model.
            @Override public String getObject() {
                return getWorkflow().getCurrentStateName();
            }

            @Override public void setObject(String object) {
                System.out.println("ERROR : i don't think this should ever be called????");
                throw new IllegalStateException("huh, why is this being set?");
            }
        };
        add(new HiddenField<String>("state", model).setMarkupId("state"));

        add(historyMaker = new AbstractDefaultAjaxBehavior() {
            @Override protected void respond(AjaxRequestTarget target) {
                IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
                String historyState = params.getParameterValue("state").toString();
                System.out.println("switching states from " + getWorkflow().getCurrentStateName() + " to " + historyState);
                getWorkflow().gotoState(historyState, target, WorkflowForm.this);
            }
        });

    }

    private FormBasedWorkflow getWorkflow() {
        return (FormBasedWorkflow) wfUtil.getWorkflowFor(this);
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
        getFormConfig().setCallbackUrl(historyMaker.getCallbackUrl().toString());
        for (HeaderItem item:getTheme().getHeaderItems()) {
            response.render(item);
        }
        wfUtil.render(this, response);
        response.render(JavaScriptReferenceHeaderItem.forReference(WORKFLOW_JS));
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

    @Override
    public Config getConfig() {
        return formConfig;
    }
}
