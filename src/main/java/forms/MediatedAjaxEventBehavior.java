package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;


// if you want to broadcast events to a parent mediator.
public abstract class MediatedAjaxEventBehavior extends AjaxEventBehavior {

    public MediatedAjaxEventBehavior(String event) {
        super(event);
    }

    @Override
    protected final void onEvent(final AjaxRequestTarget target) {
        new AjaxMediator(getComponent(), getEvent(), target, new Runnable() {
            @Override public void run()  {
                onEvent(getComponent(), getEvent(), target);
            }
        }).run();
    }

    protected abstract void onEvent(Component component, String event, AjaxRequestTarget target);


}
