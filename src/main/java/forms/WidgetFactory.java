package forms;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import javax.annotation.Nullable;
import java.io.Serializable;

public abstract class WidgetFactory {

    private boolean usePropertyAsName = false;
    private EventBus eventBus;

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    public WidgetFactory withEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
        return this;
    }

    public abstract Component createWidget(String id, WidgetConfig config, IModel<?>... models);

    public <T extends WidgetFactory> T usingPropertyAsName() {
        usePropertyAsName = true;
        return (T) this;
    }

    public final Component create(String id, WidgetConfig config, IModel<?>... models) {
        Preconditions.checkState(eventBus!=null);
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
            component.add(new AjaxEventBehavior(event) {
                // on ajax event, call mediator.  (the component itself doesn't do anything but parent mediator gets a chance to react).
                @Override protected void onEvent(final AjaxRequestTarget target) {
                    eventBus.post(new WfAjaxEvent(event, target, getComponent()));
                }
            });
        }
    }

    protected void preCreate(WidgetConfig config, IModel<?>[] formModel) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getName().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

    @Nullable
    protected IModel<?> createModel(WidgetConfig config, IModel <?> formModel) {
        String propertyExpression = config.getProperty();
        return null;
        //return createModelForId(propertyExpression);
    }

    public EventBus getEventBus() {
        return eventBus;
    }


    public static class WfAjaxEvent implements Serializable {
        private String event;
        private AjaxRequestTarget target;
        private Component component;

        public WfAjaxEvent(String event, AjaxRequestTarget target, Component component) {
            this.event = event;
            this.target = target;
            this.component = component;
        }
    }

}
