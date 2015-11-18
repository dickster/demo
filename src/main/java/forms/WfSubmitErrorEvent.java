package forms;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class WfSubmitErrorEvent {

    private AjaxRequestTarget target;
    private Form form;

    public WfSubmitErrorEvent(AjaxRequestTarget target, Form form) {
        this.target = target;
        this.form = form;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public Form getForm() {
        return form;
    }
}
