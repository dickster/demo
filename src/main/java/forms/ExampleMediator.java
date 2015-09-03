package forms;

import forms.DynamicForm.EventPropogation;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class ExampleMediator implements Mediator {

    public static final String SOME_IMPORTANT_COMPONENT_ID = "sicid";

    @Override
    public EventPropogation before(Component c, Object event, AjaxRequestTarget target) {
        if (c.getId().equals(SOME_IMPORTANT_COMPONENT_ID)) {
            if (c.getDefaultModelObject()==null) {
                // boo. i will intercept this and do something...
                c.error("blah blah blah...can't be null");
                //target.add(/*some error widget*/);
                return EventPropogation.EVENT_STOP;
            }
        }
        return EventPropogation.EVENT_PROPOGATE;
    }

    @Override
    public void after(Component c, Object event, AjaxRequestTarget target) {

    }
}
