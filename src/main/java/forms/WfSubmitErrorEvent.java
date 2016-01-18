package forms;

import forms.widgets.config.HasConfig;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;

public class WfSubmitErrorEvent extends WfErrorEvent {

    // marker class...just used to distinguish error from success.
    public <T extends FormComponent & HasConfig> WfSubmitErrorEvent(AjaxRequestTarget target, T component, Form form) {
        super(form.getFeedbackMessages(), target, component);
    }

    public Form getForm() {
        return ((FormComponent)getComponent()).getForm();
    }
}
