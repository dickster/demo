package forms.widgets;


import com.google.common.base.Preconditions;
import demo.FeedbackListener;
import demo.FeedbackState;
import demo.ISection;
import forms.Div;
import forms.config.Config;
import forms.config.HasConfig;
import forms.config.SectionPanelConfig;
import forms.model.SectionModel;
import forms.util.WfUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

// new SectionPanel(id);   inherit model = new PropertyModel("foo.bar") ---> foo.bar[]
// assume array for now. requires GroupConfig with min/max etc...
// based on inherited model

public class SectionPanel<T extends Serializable> extends Panel implements FeedbackListener, ISection, HasConfig {

    private static final String SELECT_LAST_TAB_JS = "$('#%s').tabPanel.selectLastTab()";
    private static final String BLANK_SLATE_ID = "blankSlate";
    private static final String TAB_PANEL_ID = "panel";
    private static final String TAB_PANEL_INIT = "ez.tabPanel().init(%s)";
    private static final String SET_STATUS_JS = "document.getElementById('%s').tabPanel.setStatus('%s');";

    private static final JavaScriptHeaderItem TAB_PANEL_JS = JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(SectionPanel.class, "sectionPanel.js"));
    private static final CssHeaderItem TAB_PANEL_CSS = CssHeaderItem.forReference(new CssResourceReference(SectionPanel.class,"sectionPanel.css"));
    private final SectionPanelConfig config;

    //private final IndexedModel<T> model;

    private Model<String> header;
    private boolean mandatory;
    private boolean canAdd = true;
    private String addTooltip;
    private Enum<?> status = FeedbackState.VOID;
    private Component statusIcon;
    private WebMarkupContainer panel;
    private @SpringBean WfUtil wfUtil;


    public SectionPanel(final String id, SectionPanelConfig config) {
        super(id);
        this.config = config;
        setOutputMarkupId(true);
        this.header = Model.of(config.getTitle());
        // assumes we are getting a list here...need another constructor for single entities.
        // if supermodel is !instanceof List, setDefaultModel(FixedIndexedModel(blah); etc...
           }

    protected int getInitialIndex() {
        return 0;
    }

    public SectionPanel withHeader(String header) {
        this.header = Model.of(header);
        return this;
    }

    protected WebMarkupContainer createBlankSlate(String id) {
        // override to provide your own panel when you have no tabs.
        return new WebMarkupContainer(id);
    }

    public Enum<?> getStatus() {
        return status;
    }

    public SectionPanel<T> setStatus(Enum <?> status) {
        this.status = status;
        return this;
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
            @Override public Integer getObject() {
                int size = getSectionModel().size();
                return canAdd ? size + 1 : size;   // make room for one tab to house the add button.
            }
        };

        tabsContainer.add(new Label("header", getHeaderModel()));
        tabsContainer.add(new Loop("tabs", tabCount) {
            @Override protected void populateItem(final LoopItem item) {
                final int index = item.getIndex();
                // note : the "Add" tab is the last item added in this loop.
                item.add(index < getSectionModel().size() ? new EasyTab("tab", index) : new EasyAdditionTab("tab", index));
            }

            @Override
            protected LoopItem newItem(final int iteration) {
                return newTabContainer(iteration);
            }
        });

        // TODO : put css class responsibility in .js? remove attribute appender.
        tabsContainer.add(statusIcon = new WebMarkupContainer("status").setOutputMarkupId(true).add(new AttributeAppender("class", getStatusCssModel())));

        // TODO : take this method out of tab.
        form.add(panel = createPanel(TAB_PANEL_ID));
        panel.setOutputMarkupId(true);

//        panelContainer.add(createBlankSlate(BLANK_SLATE_ID));
    }


    @Override
    protected IModel<?> initModel() {
        return new SectionModel<T>((IModel<List<T>>) super.initModel());
    }

    protected WebMarkupContainer createPanel(String id) {
        return new Div(id, config.getPanelConfig());

    }

    private IModel<String> getStatusModel() {
        return new Model<String>() {
            @Nullable
            @Override public String getObject() {
                return getStatusCss(status);
            }
        };
    }

    private Model<String> getStatusCssModel() {
        return new Model<String>() {
            @Nullable
            @Override public String getObject() {
                return getStatusCss();
            }
        };
    }

    @Nullable
    private String getStatusCss() {
        return getStatusCss(status);
    }

    private @Nullable
    String getStatusCss(Enum <?> status) {
        // turn status into css class.
        if (status==null) {
            return null;
        }
        try {
            Method method = status.getClass().getDeclaredMethod("getCss");
            return (String)method.invoke(status);
        } catch (Exception e) {
            return getStatusCssFor(status);
        }
    }

    protected String getStatusCssFor(Enum<?> state) {
        throw new IllegalAccessError("if your state class " + state.getClass().getSimpleName() + " doesn't implement 'getCss' then you need to override this method");
    }

    protected void addTab(AjaxRequestTarget target) {
        getSectionModel().add(createNewTabData(getCurrentData()));
        target.add(SectionPanel.this);
    }

    protected T createNewTabData(T data) {
        try {
            Class<T> clazz = (Class<T>) data.getClass();
            return clazz.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        throw new IllegalStateException("can't create new tab for class " + data.getClass().getSimpleName());
    }

    protected T getCurrentData() {
        return getSectionModel().getObject();
    }

    private Component newDeleteButton(String id, final int index) {
        return new AjaxLink(id) {
            @Override public boolean isVisible() {
                return getSectionModel().size()>1 || !isMandatory();   // if tab is last one and it's a mandatory field then don't show this button.  e.g. can't delete only driver. (but you can delete only conviction).
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
                attributes.getAjaxCallListeners().add(new AjaxLoadingListener());
            }
        };
    }

    protected void deleteTab(AjaxRequestTarget target, int index) {
        getSectionModel().delete(index);
    }

    protected boolean isMandatory() {
        return mandatory;
    }

    private SectionModel<T> getSectionModel() {
        return (SectionModel<T>) getDefaultModel();
    }

    protected LoopItem newTabContainer(final int index) {
        return new LoopItem(index);
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

    private Enum<?> getLoadingState() {
        return FeedbackState.LOADING;
    }

    @Nullable
    private String getAddTooltip() {
        if (addTooltip!=null) return addTooltip;
        String hdr = header.getObject();
        if (StringUtils.isNotBlank(hdr)) {
            return "Add " + hdr;
        }
        return null;
    }

    public IModel<String> getHeaderModel() {
        return new Model<String>() {
            @Override public String getObject() {
                int tabCount = getSectionModel().size();
                return String.format("%s%s", header.getObject(), (tabCount > 1 ? " (" + tabCount + ")" : ""));
            }
        };
    }

    public <X extends SectionPanel> X allowingOneOrMore() {
        mandatory = true;
        return (X) this;
    }

    public <X extends SectionPanel> X allowingZeroOrMore() {
        mandatory = false;
        return (X) this;
    }

    public <X extends SectionPanel> X allowingOnlyOne() {
        canAdd = false;
        return (X) this;
    }

    public <T extends SectionPanel> T withAddTooltip(String tooltip) {
        Preconditions.checkState(canAdd, "you must be able to add for this tooltip to show up.");
        addTooltip = tooltip;
        return (T) this;
    }

    @Override
    public void hasError(AjaxRequestTarget target, FeedbackMessage msg) {
        // TODO : generate useful error message. put status on proper tab etc... give focus to that tab?
        if (contains(msg.getReporter(),true) ) {
            setStatus(FeedbackState.HAS_ERROR);
            target.add(statusIcon);
        }
    }

    @Override
    public void resetErrors(AjaxRequestTarget target) {
        setStatus(FeedbackState.VOID);
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


    // ---------------------------------------------------------------
    // ---- INNER CLASSES --------------------------------------------
    // ---------------------------------------------------------------

    public class AjaxLoadingListener extends AjaxCallListener {
        private String css;

        public AjaxLoadingListener() {
            css = getStatusCss(getLoadingState());
            onBefore(String.format(SET_STATUS_JS, SectionPanel.this.getMarkupId(), css));
        }
    }


    public class HeaderOptions implements Serializable {
        public String minWidth = "7em";
        public String maxWidth = "10em";
    }


    public class EasyAdditionTab extends Fragment {

        private EasyAdditionTab(String id, int index) {
            super(id, "additionTabFragment", SectionPanel.this);
            add(new AjaxButton("add") {
                @Override
                public boolean isVisible() {
                    return canAdd;
                }

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    super.onSubmit(target, form);
// for DEBUGGING only....
                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException e) {
                    }
                    addTab(target);
                }

                @Override
                protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                    super.updateAjaxAttributes(attributes);
                    attributes.getAjaxCallListeners().add(new AjaxLoadingListener());
                }

            });
            setRenderBodyOnly(true);
        }

    }

    public class EasyTab extends Fragment {

        private int index;
        private String label;
        private String titleInput;

        public EasyTab(String id, int index) {
            super(id, "tabFragment", SectionPanel.this);
            this.label = getSectionModel().getObject(index).toString();
            this.index = index;

            final AjaxSubmitLink titleLink = new AjaxSubmitLink("link") {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    getSectionModel().setIndex(EasyTab.this.index);
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

        protected FormComponent usingAsTitle(FormComponent input) {
            titleInput = "#"+input.setOutputMarkupId(true).getMarkupId();  // set the jquery selector of this component.
            return input;
        }

        public String getTitleInput() {
            return titleInput;
        }

    }


}


