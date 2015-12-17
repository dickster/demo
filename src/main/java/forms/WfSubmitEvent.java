package forms;

import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class WfSubmitEvent extends WfEvent<String> {

    private final AjaxRequestTarget target;
    private final Form<?> form;
    private final Component component;

    public WfSubmitEvent(AjaxRequestTarget target, Component component, Form<?> form) {
        super(new WfUtil().getComponentName(component));
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

    public boolean is(String name) {
        // note : technically, i shouldn't
        if (getName().equalsIgnoreCase(name)) {
            if (!getName().equals(name)) System.out.println("hmmm...i'm assuming you meant " + getName() + " but you passed a similar button name of " + name);
            return true;
        }
        return false;
    }

    // ALIAS for is.
    public boolean isButton(String name) {
        return is(name);
    }
}
