package forms;

import com.google.common.eventbus.EventBus;
import forms.WidgetFactory.WfAjaxEvent;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;


// if you want to broadcast events to a parent mediator.
public abstract class MediatedAjaxEventBehavior extends AjaxEventBehavior {

    private Component component;
    private EventBus eventBus;

    public MediatedAjaxEventBehavior(String event, EventBus bus) {
        super(event);
        this.eventBus = bus;
    }


    // new TextField().add(new MediatedAjaxEventBehavior("onchange"));

    @Override
    protected final void onEvent(final AjaxRequestTarget target) {
        eventBus.post(new WfAjaxEvent(getEvent(), target, getComponent()));
    }

    protected abstract void onEvent(Component component, String event, AjaxRequestTarget target);

}
