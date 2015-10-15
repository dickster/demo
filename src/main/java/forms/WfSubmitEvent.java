package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class WfSubmitEvent extends WfAjaxEvent {

    public static final String SUBMIT = "SUBMIT";

    public WfSubmitEvent(AjaxRequestTarget target, Component component) {
        super(SUBMIT, target, component);
    }
}
