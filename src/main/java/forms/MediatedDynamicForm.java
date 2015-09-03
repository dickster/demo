package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import javax.annotation.Nonnull;

public class MediatedDynamicForm extends DynamicForm implements Mediator {

    private Mediator mediator;

    public MediatedDynamicForm(String id, @Nonnull Mediator mediator) {
        super(id);
        this.mediator = mediator;
    }

    @Override
    public EventPropogation before(Component c, Object event, AjaxRequestTarget target) {
        return mediator.before(c, event, target);
    }

    @Override
    public void after(Component c, Object event, AjaxRequestTarget target) {
        mediator.after(c,event,target);
    }
}
