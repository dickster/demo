package forms;

import com.google.common.collect.Sets;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import java.util.Set;

public class WfSubmitEvent extends WfEvent<String> {

    private final Form<?> form;


    public WfSubmitEvent(AjaxRequestTarget target, Component component, Form<?> form) {
        super(component.getPath(), target, component);
        this.form = form;
    }

    public Form<?> getForm() {
        return form;
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
