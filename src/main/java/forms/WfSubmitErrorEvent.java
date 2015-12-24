package forms;

import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class WfSubmitErrorEvent extends WfErrorEvent {

    private final AjaxRequestTarget target;
    private final Component component;
    private final Form form;

    // marker class...just used to distinguish error from success.
    public <T extends Component & HasConfig> WfSubmitErrorEvent(AjaxRequestTarget target, T component, Form form) {
        super(form.getFeedbackMessages());
        this.target = target;
        this.component = component;
        this.form = form;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public Component getComponent() {
        return component;
    }

    public Form getForm() {
        return form;
    }
}
