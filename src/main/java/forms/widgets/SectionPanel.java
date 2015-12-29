package forms.widgets;


import demo.FeedbackListener;
import demo.FeedbackState;
import demo.ISection;
import forms.Workflow;
import forms.model.WfCompoundPropertyModel;
import forms.util.WfUtil;
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
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.MarkupCache;
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
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import javax.annotation.Nullable;
import java.util.List;

// new SectionPanel(id);
//
// inherit model = new PropertyModel("foo.bar") ---> foo.bar[]
// assume array for now. requires GroupConfig with min/max etc...
// based on inherited model


public class SectionPanel extends Panel implements FeedbackListener, ISection, HasConfig {

    private static final String SELECT_LAST_TAB_JS = "$('#%s').tabPanel.selectLastTab()";
    private static final String BLANK_SLATE_ID = "blankSlate";
    private static final String TAB_PANEL_INIT = "ez.tabPanel().init(%s)";
    // change this to jquery ui code.    tabPanel('setStatus', '<value>');
    private static final String SET_STATUS_JS = "document.getElementById('%s').tabPanel.setStatus('%s');";

    private static final JavaScriptHeaderItem TAB_PANEL_JS = JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(SectionPanel.class, "sectionPanel.js"));
//    private static final CssHeaderItem TAB_PANEL_CSS = CssHeaderItem.forReference(new CssResourceReference(SectionPanel.class,"sectionPanel.css"));

    // TODO : for blank slate, have the template include an id of element to be used.
    // it must be styled to fit in same size???
    // .: blank slate only works IFF you have template.

    private int currentIndex;
    private SectionPanelConfig config;
    private Model<String> header;
//    private boolean mandatory = config.mandatory;
//    private boolean canAdd = true;
//    private String addTooltip;
    private Enum<?> status = FeedbackState.VOID;
    private Component statusIcon;
    private Component panel;
    private MarkupCache list;

    public SectionPanel(final String id, SectionPanelConfig config) {
        super(id);
        this.config = config;
        this.header = Model.of(config.getTitle());
    }

//    protected int getInitialIndex() {
//        return 0;
//    }  // does this ever need to be configurable?
//
//    public Enum<?> getStatus() {
//        return status;
//    }

//    public SectionPanel<T> setStatus(Enum <?> status) {
//        this.status = status;
//        return this;
//    }

    private void setIndex(int index) {
        this.currentIndex = index;
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
                return config.canAdd ? size + 1 : size;
            }
        };

        tabsContainer.add(new Label("header", getHeaderModel()));
        tabsContainer.add(new Loop("tabs", tabCount) {
            @Override
            protected void populateItem(final LoopItem item) {
                final int index = item.getIndex();
                // note : the "Add" tab is the last item added in this loop.
                item.add(index < getList().size() ? new EasyTab("tab", index) : new EasyAdditionTab("tab", index));
            }

            @Override
            protected LoopItem newItem(final int iteration) {
                return newTabContainer(iteration);
            }
        });

        // TODO : put css class responsibility in .js? remove attribute appender.
//        tabsContainer.add(statusIcon = new WebMarkupContainer("status").setOutputMarkupId(true).add(new AttributeAppender("class", getStatusCssModel())));

        // TODO : take this method out of tab.
        form.add(panel = createPanel());
        setIndex(0); // getInitialIndex()...maybe based on current value?
        panel.setOutputMarkupId(true);
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
                Workflow workflow = WfUtil.getWorkflow(this);
                for (int i=0; i<config.getConfigs().size(); i++) {
                    Config c = config.getConfigs().get(i);
                    add(workflow.createWidget(newChildId(), c));
                }
            }
        };
        container.add(panel);
        return container;
    }

//    private IModel<String> getStatusModel() {
//        return new Model<String>() {
//            @Nullable
//            @Override public String getObject() {
//                return getStatusCss(status);
//            }
//        };
//    }

//    private Model<String> getStatusCssModel() {
//        return new Model<String>() {
//            @Nullable
//            @Override public String getObject() {
//                return getStatusCss();
//            }
//        };
//    }

//    @Nullable
//    private String getStatusCss() {
//        return getStatusCss(status);
//    }

//    private @Nullable
//    String getStatusCss(Enum <?> status) {
//        // turn status into css class.
//        if (status==null) {
//            return null;
//        }
//        try {
//            Method method = status.getClass().getDeclaredMethod("getCss");
//            return (String)method.invoke(status);
//        } catch (Exception e) {
//            return getStatusCssFor(status);
//        }
//    }

//    protected String getStatusCssFor(Enum<?> state) {
//        throw new IllegalAccessError("if your state class " + state.getClass().getSimpleName() + " doesn't implement 'getCss' then you need to override this method");
//    }

    protected void addTab(AjaxRequestTarget target) {
        getList().add(createNewTabData(getCurrentData()));
        target.add(SectionPanel.this);
    }

    // TODO : replace this with a spring bean = "sectionPanelHandler".
    // it will handle data creation, status, ajax, etc...
    protected Object createNewTabData(Object data) {
        try {
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
// for DEBUGGING only.....
                try { Thread.sleep(500);  } catch (InterruptedException e) { }
// ........
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
    }

    protected boolean isMandatory() {
        return config.mandatory;
    }

    protected LoopItem newTabContainer(final int index) {
        return new LoopItem(index);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
//        response.render(TAB_PANEL_JS);
 //       response.render(TAB_PANEL_CSS);
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


// ---------------------------------------------------------------
    // ---- INNER CLASSES --------------------------------------------
    // ---------------------------------------------------------------

//    public class AjaxLoadingListener extends AjaxCallListener {
//        private String css;
//
//        public AjaxLoadingListener() {
//            css = getStatusCss(getLoadingState());
//            onBefore(String.format(SET_STATUS_JS, SectionPanel.this.getMarkupId(), css));
//        }
//    }


//    public class HeaderOptions implements Serializable {
//        public String minWidth = "7em";
//        public String maxWidth = "10em";
//    }
//

    public class EasyAdditionTab extends Fragment {

        private EasyAdditionTab(String id, int index) {
            super(id, "additionTabFragment", SectionPanel.this);
            add(new AjaxButton("add") {
                @Override
                public boolean isVisible() {
                    return config.canAdd;
                }

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    super.onSubmit(target, form);
// for DEBUGGING only...
                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException e) {
                    }
                    addTab(target);
                }

                @Override
                protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                    super.updateAjaxAttributes(attributes);
//                    attributes.getAjaxCallListeners().add(new AjaxLoadingListener());
                }

            });
            setRenderBodyOnly(true);
        }

    }

    public class EasyTab extends Fragment {

        private int index;
        private String label;

        public EasyTab(String id, int index) {
            super(id, "tabFragment", SectionPanel.this);
            this.label = getList().get(index).toString();
            this.index = index;

            final AjaxSubmitLink titleLink = new AjaxSubmitLink("link") {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    setIndex(EasyTab.this.index);
                    target.add(SectionPanel.this);
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    //TODO : handle validation here...?
                    target.add(this);

                }
            };
            titleLink.add(newTitle("title", label, index));
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


