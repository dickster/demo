package forms;

import forms.spring.WfNavigator;
import forms.util.WfUtil;
import forms.widgets.config.*;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.renderStrategy.DeepChildFirstVisitor;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.visit.IVisit;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class WorkflowForm extends Panel implements HasConfig, HasTemplate {

    private @Inject Toolkit toolkit;
    private @Inject WfNavigator wfNavigator;

    private Form form;
    private FeedbackPanel feedback;
    private AbstractDefaultAjaxBehavior historyMaker;
    private Template template;
    private ListView content;
    private WfFormState state;
    private Component visitorKludge;

    public WorkflowForm(@Nonnull String id, @Nonnull WfFormState state) {
        super(id);
        this.state = state;
        setOutputMarkupId(true);
        add(new RenderingBehavior());  // TODO : should this be a prototype bean???
        setupHistory();

        // placeholder to be replaced based on formConfig.
        add(new WebMarkupContainer("form").add(new WebMarkupContainer("content")));
        add(new Label("subheader", getSubHeader()));

        update(getFormConfig());
    }


    @Override
    public IMarkupFragment getMarkup() {
        return super.getMarkup();
    }

    public IModel<?> getSubHeader() {
        return new Model<String>() {
            @Override public String getObject() {
                return getFormConfig().getTitle();
            }
        };
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

    private Workflow<?> getWorkflow() {
        return wfNavigator.getWorkflow(this);
    }

    public WorkflowForm withFormValidator(IFormValidator validator) {
        form.add(validator);
        return this;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        getWorkflow().register(this);
        add(createFeedbackPanel());
        getTheme().apply(form);
    }

    private Component createFeedbackPanel() {
        FeedbackPanelConfig feedbackConfig = getFormConfig().getFeedbackConfig();
        if (feedbackConfig==null) {
            System.out.println("WARNING : using default feedback panel. you probably should supply your own. ");
            feedbackConfig = new FeedbackPanelConfig();
        }
        Component panel = getWorkflow().createWidget(feedbackConfig.getId(), feedbackConfig);
        return panel;
    }

    private void update(FormConfig formConfig) {
        form = new Form("form");
        form.setOutputMarkupId(true);
        form.add(content());
        form.add(template = new Template("template", formConfig));
        addOrReplace(form);
    }

    private Component content() {
        content = new ListView<Config>("content", getFormConfig().getConfigs()) {
            @Override
            protected void populateItem(ListItem<Config> item) {
                Config c = item.getModelObject();
                Component component = getWorkflow().createWidget("el", c);
                item.add(component).setRenderBodyOnly(true);
            }
        }.setReuseItems(true);
        return content;
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
    }

    public Form getForm() {
        return form;
    }

    public FormConfig getFormConfig() {
        return state.getFormConfig();
    }

    public Component getWfComponent(final String id) {
        // TODO : should cache this??

        // argh...because the visitor doesn't support generics we have to work around getting a return value.
        // suggest : make a copy that supports generics = EZDeepChildFirstVisitor<Component, Component>();
        visitorKludge = null;   // unfortunately, can't use a local var for this...boo!
        content.visitChildren(Component.class, new DeepChildFirstVisitor() {
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
    public String getTemplateId() {
        return template.getMarkupId();
    }

    @Override
    public Config getConfig() {
        return state.getFormConfig();
    }



}
