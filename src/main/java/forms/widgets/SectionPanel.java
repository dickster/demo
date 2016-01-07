package forms.widgets;


import demo.FeedbackListener;
import demo.FeedbackState;
import demo.ISection;
import forms.Template;
import forms.Workflow;
import forms.model.WfCompoundPropertyModel;
import forms.spring.WfNavigator;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.SectionPanelConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

public class SectionPanel<T extends Component> extends Panel implements FeedbackListener, ISection, HasConfig {

    private static final String SELECT_LAST_TAB_JS = "$('#%s').tabPanel.selectLastTab()";
    private static final String BLANK_SLATE_ID = "blankSlate";
    private static final String TAB_PANEL_INIT = "ez.tabPanel().init(%s)";
    // change this to jquery ui code.    tabPanel('setStatus', '<value>');
    private static final String SET_STATUS_JS = "document.getElementById('%s').tabPanel.setStatus('%s');";

    private static final JavaScriptHeaderItem TAB_PANEL_JS = JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(SectionPanel.class, "sectionPanel.js"));
    private static final CssHeaderItem TAB_PANEL_CSS = CssHeaderItem.forReference(new CssResourceReference(SectionPanel.class,"sectionPanel.css"));

    private @Inject WfNavigator wfNavigator;


//---------------------------------------------------------------------------------
// TODO : Xstatus, blank slate, one, zero, min/max, Xtooltip configs.
// Xwrite plugin.  expand/collapse option, XcanAdd?
// current tab.
// Xwhen adding new, if blank what value  "new person", "(unnamed person)"
// Xassociate input & formatter.  title = fields[a,b,c,d]
// for blank slate, have the template include an id of element to be used.
// it must be styled to fit in same size??
// .: blank slate only works IFF you have template.
// sketch out SectionHandler interface and refactor this code to use it.   create a default one.
//---------------------------------------------------------------------------------

    private int currentIndex;
    private SectionPanelConfig config;
    private Model<String> header;
    private Enum status = FeedbackState.HAS_WARNING;
    private Component statusIcon;
    private Component panel;
    private Template template;

    public SectionPanel(final String id, SectionPanelConfig config) {
        super(id);
        this.config = config;
        this.header = Model.of(config.getTitle());
        this.currentIndex = 0;
    }

    // this should listen for section panel events and retrieve status from them.
    public Enum<?> getStatus() {
        return status;
    }

    public SectionPanel<T> setStatus(Enum <?> status) {
        this.status = status;
        return this;
    }

//    @Subscribe   TODO : allow for workflow events to be posted.
//    public void onSectionEvent(WfSectionEvent event) {
//        event.getStatus();  if (event.is(thisSection))
//              blah blah blah...
//    }

    private void setIndex(int index) {
        this.currentIndex = index;
        // this might be redundant except for calling in onInitialize()?
        Object obj = getList().get(currentIndex);
        panel.setDefaultModel(new WfCompoundPropertyModel(obj));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form form = new Form("form");
        add(form);

        final WebMarkupContainer tabsContainer = new WebMarkupContainer("tabsContainer");
        tabsContainer.setOutputMarkupId(true);
        form.add(tabsContainer);

        final IModel<Integer> tabCount = new AbstractReadOnlyModel<Integer>() {
            @Override
            public Integer getObject() {
                int size = getList().size();
                // make room for one tab to house the add button.
                return (config.canAdd & size<=config.max) ? size+1 : size;
            }
        };

        tabsContainer.add(new Label("header", getHeaderModel()));
        tabsContainer.add(new Loop("tabs", tabCount) {
            @Override
            protected void populateItem(final LoopItem item) {
                final int index = item.getIndex();
                // note : the "Add" tab is the last item added in this loop.
                item.add(index < getList().size() ? new SectionTab("tab", index) : new AddSectionTab("tab", index));
            }

            @Override
            protected LoopItem newItem(final int iteration) {
                return newTabContainer(iteration);
            }
        });

        // TODO : put css class responsibility in .js? remove attribute appender.
        tabsContainer.add(statusIcon = new WebMarkupContainer("status").setOutputMarkupId(true).add(new AttributeAppender("class", getStatusCssModel())));
        form.add(panel = createPanel());
        form.add(template = new Template("template", config.getTemplate()));
        setIndex(currentIndex);
    }

    @Override
    protected IModel<?> initModel() {
        return super.initModel();
    }

    protected Component createPanel() {
        WebMarkupContainer container = new WebMarkupContainer("container");
        RepeatingView panel = new RepeatingView("panel") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                Workflow workflow = wfNavigator.getWorkflow(this);
                for (int i=0; i<config.getConfigs().size(); i++) {
                    Config c = config.getConfigs().get(i);
                    add(workflow.createWidget(newChildId(), c));
                }
            }
        };
        container.add(panel);
        return container;
    }

    private Model<String> getStatusCssModel() {
        return new Model<String>() {
            @Nullable @Override public String getObject() {
                return getStatusCss();
            }
        };
    }

    @Nullable
    private String getStatusCss() {
        if (status instanceof HasCss) {
            return ((HasCss)status).getCss();
        }
        return status.name().toLowerCase();
    }

    protected void addTab(AjaxRequestTarget target) {
        getList().add(createNewTabData(getCurrentData()));
        setIndex(getList().size()-1);
        target.add(SectionPanel.this);
    }

    // TODO : replace this with a spring bean = "sectionPanelHandler".
    // it will handle data creation, status, ajax, etc...
    protected Object createNewTabData(Object data) {
        try {
            // TODO : should also look for a new?? method or create?? method.
            // otherwise lookup data factory. in SectionHandler
            return data.getClass().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("can't create new tab for class " + data.getClass().getSimpleName());
        }
    }

    protected Object getCurrentData() {
        return getList().get(currentIndex);
    }

    private Component newDeleteButton(String id, final int index) {
        return new AjaxLink(id) {
            @Override public boolean isVisible() {
                return getList().size()>1 || !isMandatory();   // if tab is last one and it's a mandatory field then don't show this button.  e.g. can't delete only driver. (but you can delete only conviction).
            }
            @Override public void onClick(AjaxRequestTarget target) {
                deleteTab(target, index);
                target.add(SectionPanel.this);
            }
            @Override protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
//                attributes.getAjaxCallListeners().add(new AjaxLoadingListener());
            }
        };
    }

    protected void deleteTab(AjaxRequestTarget target, int index) {
        getList().remove(index);
        if (index==currentIndex && getList().size()>0) {
            setIndex(Math.max(0, index-1));
        }
    }

    protected boolean isMandatory() {
        return config.mandatory;
    }

    protected LoopItem newTabContainer(final int index) {
        LoopItem item = new LoopItem(index);
        if (index==currentIndex) {
            item.add(new AttributeAppender("class", "active"));
        }
        return item;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(TAB_PANEL_JS);
        response.render(TAB_PANEL_CSS);
    }

    protected Component newTitle(final String titleId, final String title, final int index) {
        return new Label(titleId, title);
    }

    @Nullable
    private String getAddTooltip() {
        if (config.addTooltip!=null) return config.addTooltip;
        //try to hack together a default value. (*not localized*)
        String hdr = header.getObject();
        if (StringUtils.isNotBlank(hdr)) {
            return "Add " + hdr;
        }
        return null;
    }

    public IModel<String> getHeaderModel() {
        return new Model<String>() {
            @Override public String getObject() {
                int tabCount = getList().size();
                return String.format("%s%s", header.getObject(), (tabCount > 1 ? " (" + tabCount + ")" : ""));
            }
        };
    }

    @Override
    public void hasError(AjaxRequestTarget target, FeedbackMessage msg) {
        // TODO : generate useful error message. put status on proper tab etc... give focus to that tab?
        if (contains(msg.getReporter(),true) ) {
           // setStatus(FeedbackState.HAS_ERROR);
            target.add(statusIcon);
        }
    }

    @Override
    public void resetErrors(AjaxRequestTarget target) {
       // setStatus(FeedbackState.VOID);
        target.add(statusIcon);
    }

    @Override
    public String getTooltip() {
        return getClass().getName();
    }

    @Override
    public String getName() {
        return getTooltip();
    }

    @Override
    public String getIconCss() {
        return "";
    }

    @Override
    public Integer getOrdinal() {
        return 0;
    }

    public String getHref() {
        return "#"+getId();
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public IModel<List> getListModel() {
        return (IModel<List>) getDefaultModel();
    }

    public List getList() {
        return getListModel().getObject();
    }

    public class AddSectionTab extends Fragment {

        private AddSectionTab(String id, int index) {
            super(id, "additionTabFragment", SectionPanel.this);
            add(new AjaxButton("add") {
                @Override public boolean isVisible() {
                    return config.canAdd;
                }

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    super.onSubmit(target, form);
                    addTab(target);
                }

                @Override
                protected void onComponentTag(ComponentTag tag) {
                    super.onComponentTag(tag);
                    String tooltip = getAddTooltip();
                    if (tooltip!=null) {
                        tag.getAttributes().put("title", tooltip);
                    }
                }

                @Override
                protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                    super.updateAjaxAttributes(attributes);
//                    attributes.getAjaxCallListeners().add(new AjaxLoadingListener());
                }

            });
            setRenderBodyOnly(true);
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            if (StringUtils.isNotBlank(config.addTooltip)) {
                tag.getAttributes().put("title", config.addTooltip);
            }
        }
    }

    public class SectionTab extends Fragment {
        private String label;

        public SectionTab(String id, final int index) {
            super(id, "tabFragment", SectionPanel.this);
            this.label = getList().get(index).toString();

            final AjaxSubmitLink titleLink = new AjaxSubmitLink("link") {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    setIndex(index);
                    target.add(SectionPanel.this);
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    //TODO : handle validation here...?
                    target.add(this);
                }
            };
            String title = label;
            if (StringUtils.isBlank(title)) {
                title = config.getTitleForNewValues();
            }
            titleLink.add(newTitle("title", title, index));
            add(newDeleteButton("delete", index));
            add(titleLink);
            setRenderBodyOnly(true);
        }

        public String getTitle() {
            // not null safe. typically you will implement this yourself.
            return label;
        }

    }

}


