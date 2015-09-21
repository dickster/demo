package forms;


import forms.Mediator.MediatorType;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class WfAjaxEvent extends WfEvent<String> {
    private AjaxRequestTarget target;
    private Component component;
    private boolean stopPropogation = false;
    private MediatorType type = MediatorType.AFTER;

    public WfAjaxEvent(String event, AjaxRequestTarget target, Component component) {
        super(event);
        this.target = target;
        this.component = component;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public boolean isStopped() {
        return stopPropogation;
    }

    public void stopPropogation() {
        stopPropogation = true;
    }

    public WfAjaxEvent withType(MediatorType type) {
        this.type = type;
        return this;
    }
}
