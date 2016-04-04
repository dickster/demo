package forms;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import forms.spring.StringLoader;
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
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkflowForm extends Panel implements HasConfig {

    private @Inject Toolkit toolkit;
    private @Inject StringLoader stringLoader;
    private @Inject WfNavigator wfNavigator;

    private Form form;
    private FeedbackPanel feedback;
    private AbstractDefaultAjaxBehavior historyMaker;
    private Template template;
    private WebMarkupContainer content;
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
        update(getFormConfig());
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
        form.add(content = content());
        form.add(template = new Template("template", formConfig));
        addOrReplace(form);
    }

    private DynamicMarkup content() {
        DynamicMarkup content = new DynamicMarkup("content", "template1.html");
        Workflow<?> workflow = getWorkflow();
        List<Config> configs = getFormConfig().getConfigs();
        for (Config c:configs) {
            Component component = workflow.getWidgetFactory().createWidget(c.getId(), c);
            content.add(component);
        }
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
    public Config getConfig() {
        return state.getFormConfig();
    }



    class DynamicMarkup extends Panel {

        // TODO : add caching for this. maybe (re)use wicket's markupCache or
        // create my own spring bean based on guava??? private @Inject MarkupCache cache;

        private String source;
        private static final String TEMPLATE_BASE = "demo/resources/templates/";
        private StringLoader loader;

        public DynamicMarkup(String id, String template) {
            super(id);
            setOutputMarkupId(true);
            setRenderBodyOnly(false);
            this.source = normalize(template);
        }

        private String normalize(String source) {
            if (source==null) {
                return null;
            }
            source = source.trim();
            if (source.startsWith(TEMPLATE_BASE)) {
                source = source.substring(TEMPLATE_BASE.length());
            }
            if (!source.endsWith(".html") && source.indexOf('.')==-1) {
                System.out.println("WARNING : assuming you want to load an .html template file. i.e. [" + source +".html] as opposed to ["+source+"]");
                source = source+".html";
            }
            return source;
        }

        @Override
        public Markup getAssociatedMarkup() {
            try {
                if (source==null) {
                    // this should never happen!  if source==null, setVisible(false).
                    throw  new IllegalStateException("attempting to render an null/empty template.");
                }
                InputStream stream = new ClassPathResource(getFullPath()).getInputStream();
                String content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
                Closeables.closeQuietly(stream);
                return Markup.of(resolveLabels(content));
            } catch (IOException e) {
                throw new WorkflowException(e);
            }
        }

        private String resolveLabels(String content) {
//            if (loader!=null) {
               // TODO : replace ${label.foo} with values stored in string loader.
               //  e.g. ${name} --> "derek"
               // this should be locale sensitive.
                Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
                Matcher matcher = pattern.matcher(content);

                StringBuffer sb = new StringBuffer();
                while (matcher.find()) {
                    String value = matcher.group(1)+"-VALUE";   // use loader to get values.
                    matcher.appendReplacement(sb, value);
                }
  //          }
            return matcher.appendTail(sb).toString();
        }

        private String getFullPath() {
            return TEMPLATE_BASE+source;
        }

        public DynamicMarkup withLabelResources(StringLoader loader) {
            this.loader = loader;
            return this;
        }

    }


}
