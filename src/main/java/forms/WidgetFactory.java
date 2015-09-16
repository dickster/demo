package forms;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public abstract class WidgetFactory {

    private boolean usePropertyAsName = false;

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    public abstract Component createWidget(String id, WidgetConfig config, IModel<?>... models);

    public <T extends WidgetFactory> T usingPropertyAsName() {
        usePropertyAsName = true;
        return (T) this;
    }

    public final Component create(String id, WidgetConfig config, IModel<?>... models) {
        preCreate(config, models);
        Component component = createWidget(id, config, models);
        postCreate(component, config, models);
        return component;
    }

    protected void postCreate(final Component component, WidgetConfig config, IModel<?>... models) {
        String name = usePropertyAsName ? config.getName() : config.getProperty();
        component.setMetaData(WidgetConfig.NAME, name);
        // set mediator...listen to all published events.
        component.setOutputMarkupId(true);
        final String event = "onchange";//config.getMediatedEvent();
        if (StringUtils.isNotBlank(event)) {
            component.add(new MediatedAjaxEventBehavior(event));
        }
    }

    protected void preCreate(WidgetConfig config, IModel<?>[] formModel) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getName().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

    @NotNull
    protected IModel<?> createModel(WidgetConfig config, CompoundPropertyModel<?> formModel) {
        String propertyExpression = config.getProperty();
        return formModel.bind(propertyExpression);
    }



}
