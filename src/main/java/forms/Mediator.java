package forms;

import forms.DynamicForm.EventPropogation;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public interface Mediator {

    public EventPropogation before(Component c, Object event, AjaxRequestTarget target);

    public void after(Component c, Object event, AjaxRequestTarget target);
}
