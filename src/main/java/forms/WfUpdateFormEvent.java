package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class WfUpdateFormEvent extends WfAjaxEvent {

    private AjaxRequestTarget target;
    private Form form;

    public WfUpdateFormEvent() {
        super("UPDATE_FORM");
    }

    public Form getForm() {
        return form;
    }
}
