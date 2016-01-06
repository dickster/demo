package forms;

import com.google.common.base.Preconditions;
import demo.resources.Resource;
import forms.spring.WfNavigator;
import forms.util.WfUtil;
import forms.widgets.config.Config;
import forms.widgets.config.FeedbackPanelConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
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

    private @Inject Toolkit toolkit;
    private @Inject WfNavigator wfNavigator;

    private Component visitorKludge;

    private Form form;
    private FeedbackPanel feedback;
    private FormConfig formConfig;
    private AbstractDefaultAjaxBehavior historyMaker;
    private Template template;

    public WorkflowForm(@Nonnull String id, @Nonnull FormConfig config) {
        super(id);
        withConfig(config);
        setOutputMarkupId(true);
        add(new RenderingBehavior());

        setupHistory();

        // placeholder to be replaced based on formConfig.
        add(new WebMarkupContainer("form").add(new WebMarkupContainer("content")));
    }

    public String getSubHeader() {
        return formConfig.getTitle();
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
                System.out.println("ERROR : i don't think this should ever be called??");
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
        return (FormBasedWorkflow) wfNavigator.getWorkflow(this);
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
        getWorkflow().register(this);
        add(createFeedbackPanel());
        update(formConfig);
        getTheme().apply(form);
    }

    private Component createFeedbackPanel() {
        FeedbackPanelConfig config = formConfig.getFeedbackConfig();
        if (config==null) {
            System.out.println("WARNING : using default feedback panel. you probably should supply your own. ");
            config = new FeedbackPanelConfig();
        }
        Component panel = getWorkflow().createWidget(config.getId(), config);
        return panel;
    }

    private void update(FormConfig formConfig) {
        form = new Form("form");
        form.setOutputMarkupId(true);
        form.add(new Div("content", formConfig).setRenderBodyOnly(false));
        form.add(template = new Template("template", formConfig.getTemplate()));
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
        getFormConfig().setHistoryCallbackUrl(historyMaker.getCallbackUrl().toString());
        for (HeaderItem item:getTheme().getHeaderItems()) {
            response.render(item);
        }
        response.render(JavaScriptReferenceHeaderItem.forReference(WORKFLOW_JS));
    }

    public Form getForm() {
        return form;
    }

    public FormConfig getFormConfig() {
        return formConfig;
    }

    public Component getWfComponent(final String id) {
        // argh...because the visitor doesn't support generics we have to work around getting a return value.
        // suggest : make a copy that supports generics = EZDeepChildFirstVisitor<Component, Component>();
        visitorKludge = null;   // unfortunately, can't use a local var for this...boo!
        visitChildren(Component.class, new DeepChildFirstVisitor() {
            @Override
            public void component(Component component, IVisit<Void> visit) {
                String n = WfUtil.getComponentId(component);
                if (id.equals(n)) {
                    visitorKludge = component;
                    visit.stop();
                }
            }

            @Override
            public boolean preCheck(Component component) {
                return true;
            }
        });
        return visitorKludge;
    }

    @Override
    public Config getConfig() {
        return formConfig;
    }

}
