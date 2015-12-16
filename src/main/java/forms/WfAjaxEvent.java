package forms;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import javax.annotation.Nonnull;

@Deprecated
public class WfAjaxEvent extends WfEvent<String> {
    private AjaxRequestTarget target;
    private Component component;
    private boolean stopPropogation = false;

    public WfAjaxEvent(String event, AjaxRequestTarget target, @Nonnull Component component) {
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

    public Component getComponent() {
        return component;
    }

    public void stopPropogation() {
        stopPropogation = true;
    }

}
