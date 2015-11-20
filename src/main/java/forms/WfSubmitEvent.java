package forms;

import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class WfSubmitEvent extends WfEvent {

    private final AjaxRequestTarget target;
    private final Form<?> form;
    private final Component component;

    public WfSubmitEvent(AjaxRequestTarget target, Component component, Form<?> form) {
        super(WfUtil.getComponentName(component));
        this.target = target;
        this.form = form;
        this.component = component;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public Form<?> getForm() {
        return form;
    }

    public Component getComponent() {
        return component;
    }
}
