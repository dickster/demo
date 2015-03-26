package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.util.List;

public class DynamicForm extends Form {

    private static final String INIT_FORM = "easyForm.init(%s);";
    public static final String ID_PARAM = "id";
    public static final String EVENT_PARAM = "event";

    //    @SpringBean
    private  Toolkit toolkit;

    private FormConfig formConfig;
    private IModel<?> formModel;

    private transient FormOptions formOptions = new FormOptions();
    private AbstractDefaultAjaxBehavior behavior;

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

    public IModel<?> getFormModel() {
        return formModel;
    }

    @Override
    protected void onInitialize() {
        Preconditions.checkNotNull(formConfig);
        Preconditions.checkNotNull(getFormModel());

        super.onInitialize();

        setOutputMarkupId(true);
        setDefaultModel(getFormModel());

        final WidgetFactory factory = createWidgetFactory(toolkit);

        add(new ListView<WidgetConfig>("widgets", formConfig.getWidgetConfigs()) {
            @Override
            protected void populateItem(ListItem<WidgetConfig> item) {
                WidgetConfig config = item.getModel().getObject();
                Component widget = factory.create(config, getFormModel());
                if (widget instanceof EasyWidget) {
                    widget.setOutputMarkupId(true);
                    formOptions.addWidgetOptions(((EasyWidget) widget).getOptions());
                }
                item.add(widget);
            }
        });

        addAjaxCallback();

        getTheme().apply(this);
    }

    private Theme getTheme() {
        return toolkit.getTheme();
    }

    private WidgetFactory createWidgetFactory(Toolkit toolkit) {
        return this.toolkit.getWidgetFactory();
    }

    private void addAjaxCallback() {
        behavior = new AbstractDefaultAjaxBehavior() {
            @Override protected void respond(AjaxRequestTarget target) {
                IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
                String widgetId = params.getParameterValue(ID_PARAM).toString();
                String ajaxEvent = params.getParameterValue(EVENT_PARAM).toString();
                DynamicForm.this.handleResponse(widgetId, ajaxEvent, target);
            }
        };
        add(behavior);
        formOptions.callback = behavior.getCallbackUrl().toString();
    }

    private final void handleResponse(@Nonnull final String widgetId, @Nonnull final String ajaxEvent, final AjaxRequestTarget target) {
        if (respond(widgetId, ajaxEvent, target)) {
            return;
        }
        visitChildren(Component.class, new IVisitor<Component, Object>() {
            @Override public void component(Component widget, IVisit<Object> visit) {
                if (widgetId.equals(widget.getMarkupId())) {
                    if (widget instanceof Ajaxable) {
                        Ajaxable ajaxWidget = (Ajaxable) widget;
                        ajaxWidget.respond(target, ajaxEvent);
                        visit.stop();
                    }
                }
            }
        });
    }

    protected boolean respond(String widgetId, String ajaxEvent, AjaxRequestTarget target) {
        // override if you want custom behaviour.
        return false;
    }


    class FormOptions {
        String id = getMarkupId();
        List<WidgetOptions> widgetOptions = Lists.newArrayList();
        String callback;
        Boolean skipValidate;

        public FormOptions addWidgetOptions(WidgetOptions o) {
            widgetOptions.add(o);
            return this;
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        getTheme().renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_FORM, new Gson().toJson(formOptions))));
    }
}