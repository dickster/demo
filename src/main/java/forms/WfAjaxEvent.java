package forms;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import javax.annotation.Nonnull;

// is this really used anymore??
public class WfAjaxEvent extends WfEvent<String> {
    private boolean stopPropogation = false;

    public WfAjaxEvent(String event, AjaxRequestTarget target, @Nonnull Component component) {
        super(event);
    }

    public boolean isStopped() {
        return stopPropogation;
    }

    public void stopPropogation() {
        stopPropogation = true;
    }

}
