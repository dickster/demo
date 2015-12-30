package forms;

import com.google.common.collect.Sets;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import java.util.Set;

public class WfSubmitEvent extends WfEvent<String> {

    private final AjaxRequestTarget target;
    private final Form<?> form;
    private final Component component;


    public WfSubmitEvent(AjaxRequestTarget target, Component component, Form<?> form) {
        super(WfUtil.getComponentId(component));
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
        // if the event name is button.next and next will both match if param = "name".
        String n = getName().toLowerCase();
        Set <String> names = Sets.newHashSet(n, n.replace("button.", ""));
        if (names.contains(name.toLowerCase())) {
            return true;
        }
        return false;
    }

    // ALIAS for is.
    public boolean isButton(String name) {
        return is(name);
    }
}
