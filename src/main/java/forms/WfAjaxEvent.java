package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class WfAjaxEvent extends WfEvent<String> {
    private AjaxRequestTarget target;
    private Component component;

    public WfAjaxEvent(String event, AjaxRequestTarget target, Component component) {
        super(event);
        this.target = target;
        this.component = component;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
