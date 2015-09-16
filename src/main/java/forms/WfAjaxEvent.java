package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

public class WfAjaxEvent implements Serializable {
    private String event;
    private AjaxRequestTarget target;
    private Component component;
    private boolean stopPropogation = false;
    private Mediator.MediatorType type = Mediator.MediatorType.POST;

    public WfAjaxEvent(String event, AjaxRequestTarget target, Component component) {
        this.event = event;
        this.target = target;
        this.component = component;
    }

    public WfAjaxEvent withType(Mediator.MediatorType type) {
        this.type = type;
        return this;
    }

    public void stopPropogation() {
        this.stopPropogation = true;
    }


    public boolean isStopped() {
        return stopPropogation;
    }
}
