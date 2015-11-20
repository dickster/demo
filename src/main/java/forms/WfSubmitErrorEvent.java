package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class WfSubmitErrorEvent extends WfSubmitEvent {

    // marker class...just used to distinguish error from success.
    public WfSubmitErrorEvent(AjaxRequestTarget target, Component component, Form form) {
        super(target, component, form);
    }

}
