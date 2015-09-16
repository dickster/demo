package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.List;
import java.util.Map;

public class DynamicForm extends Form  {

    private static final String INIT_FORM = "easyForm.init(%s);";
    public static final String ID_PARAM = "id";
    public static final String EVENT_PARAM = "event";

    enum EventPropogation { EVENT_STOP, EVENT_PROPOGATE };

    private @SpringBean Toolkit toolkit;

    // needs access to workflow. (not necessarily though, just in typical case?)

    private FormConfig formConfig;
    private IModel<?> formModel;
    private transient FormOptions formOptions;
    private AbstractDefaultAjaxBehavior behavior;
    private String expectedAcordVersion; // TODO : set valid default here...

    public DynamicForm(String id) {
        super(id);
    }

    public DynamicForm withConfig(FormConfig config) {
        this.formConfig = config;
        return this;
    }

    public DynamicForm withModel(IModel<?> model) {
        this.formModel = model;
        return this;
    }

    public DynamicForm withFormValidator(IFormValidator validator) {
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

        formConfig.validateAcordVersion(expectedAcordVersion);

        super.onInitialize();

        setOutputMarkupId(true);
        setDefaultModel(getFormModel());

        final WidgetFactory factory = createWidgetFactory(toolkit);

        formOptions = new FormOptions();
        add(new ListView<WidgetConfig>("widgets", formConfig.getWidgetConfigs()) {
            @Override
            protected void populateItem(ListItem<WidgetConfig> item) {
                WidgetConfig config = item.getModel().getObject();
                Component widget = createWidget("widget", config, factory);
                formOptions.add(widget, config.getName());
                if (widget instanceof HasWidgetOptions) {
                    WidgetOptions options = getOptions(widget);
                    formOptions.add(widget, options);
                }
                item.add(widget);
            }
        });

        getTheme().apply(this);
    }

    protected Component createWidget(String id, WidgetConfig config, WidgetFactory factory) {
        return factory.create(id, config, getFormModel());
    }

    private WidgetOptions getOptions(Component widget) {
        WidgetOptions options = ((HasWidgetOptions) widget).getOptions();
        return options;
    }

    private Theme getTheme() {
        return toolkit.getTheme();
    }

    private WidgetFactory createWidgetFactory(Toolkit toolkit) {
        return this.toolkit.createWidgetFactory();
    }

    public <T extends DynamicForm> T supportingAcordVersion(String version) {
        this.expectedAcordVersion = version;
        return (T) this;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        for (HeaderItem item:getTheme().getHeaderItems()) {
            response.render(item);
        }
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_FORM, new Gson().toJson(formOptions))));
    }

    class FormOptions {
        String id = getMarkupId();
        Map<String, WidgetOptions> widgetOptions = Maps.newHashMap();
        Map<String, String> idToName = Maps.newHashMap();
        Boolean skipValidation;

        FormOptions() {
        }

        public FormOptions add(Component widget, WidgetOptions o) {
            widgetOptions.put(widget.getMarkupId(), o);
            return this;
        }

        public void add(Component widget, String name) {
            idToName.put(widget.getMarkupId(), name);
        }
    }

}