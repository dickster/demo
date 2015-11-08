package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import demo.PageLayout;
import forms.config.Config;
import forms.config.FormConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nullable;
import java.util.Map;

public class WorkflowForm extends Form  {

    // this calls layout and initializes all widgets.
    // TODO : use gridstack to handle layouts?
    private static final String INIT_FORM = "var init_form_options = %s;";

    private @SpringBean Theme theme;
    private @SpringBean WidgetFactory factory;

    private FormConfig formConfig;
    private IModel<?> formModel;
    private String expectedAcordVersion; // TODO : set valid default here...
    private PageLayout layout = null;
    private boolean useDefaultLayout = false;

    public WorkflowForm(String id, FormConfig config) {
        super(id);
        withConfig(config);
    }

    public WorkflowForm withConfig(FormConfig config) {
        this.formConfig = config;
        return this;
    }

    public WorkflowForm withLayout(PageLayout layout) {
        this.layout = layout;
        return this;
    }

    public WorkflowForm withDefaultLayout() {
        this.useDefaultLayout = true;
        this.layout = null;
        return this;
    }

    public WorkflowForm withModel(IModel<?> model) {
        this.formModel = model;
        return this;
    }

    public WorkflowForm withFormValidator(IFormValidator validator) {
        add(validator);
        return this;
    }

    public IModel<?> getFormModel() {
        return formModel;
    }

    @Override
    protected void onInitialize() {
        Preconditions.checkNotNull(formConfig);
        Preconditions.checkNotNull(getFormModel());

        super.onInitialize();
        formConfig.validateAcordVersion(expectedAcordVersion);

        setOutputMarkupId(true);
        setDefaultModel(getFormModel());

        add(new Group("content", formConfig, getFormModel()));

        getTheme().apply(this);
    }

    private JsonOptions getOptions(Component widget) {
        JsonOptions options = ((HasJsonOptions) widget).getOptions();
        return options;
    }

    private Theme getTheme() {
        return theme;
    }

    public <T extends WorkflowForm> T supportingAcordVersion(String version) {
        this.expectedAcordVersion = version;
        return (T) this;
    }

    @Override
    protected void onSubmit() {
        super.onSubmit();
        System.out.println("submitting!!!!");
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        for (HeaderItem item:getTheme().getHeaderItems()) {
            response.render(item);
        }
        String optionsJson = new Gson().toJson(getFormOptions());
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_FORM, optionsJson)));
    }

    private FormOptions getFormOptions() {
        final FormOptions formOptions = new FormOptions();
        return formOptions;
    }

    protected @Nullable PageLayout getLayout() {
        return (layout == null && useDefaultLayout) ? new PageLayout().withDefaultLayout(this) : layout;
    }

    public class FormOptions {
        String id = getMarkupId();
        PageLayout layout;
        Map<String, JsonOptions> widgetOptions = Maps.newHashMap();
        Map<String, String> nameToId = Maps.newHashMap();
        Boolean skipValidation;

        public FormOptions() {
            WorkflowForm.this.visitChildren(Component.class, new IVisitor<Component, Void>() {
                @Override
                public void component(Component widget, IVisit visit) {
                    add(widget, widget.getMetaData(Config.NAME));
                    if (widget instanceof HasJsonOptions) {
                        add(widget, getOptions(widget));
                    }
                }
            });
            layout = getLayout();
        }

        public FormOptions add(Component widget, JsonOptions o) {
            widgetOptions.put(widget.getMarkupId(), o);
            return this;
        }

        public void add(Component widget, String name) {
            nameToId.put(name, widget.getMarkupId());
        }
    }


}