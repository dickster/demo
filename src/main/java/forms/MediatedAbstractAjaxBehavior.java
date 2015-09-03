package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

// if you want to broadcast events to a parent mediator.
public abstract class MediatedAbstractAjaxBehavior extends AbstractDefaultAjaxBehavior {

    public static final Object ABSTRACT_EVENT = "ABSTRACT_EVENT_TYPE";

    private Object event;


    public MediatedAbstractAjaxBehavior() {
        super();
        this.event = ABSTRACT_EVENT;
    }

    @Override
    protected void onBind() {
        super.onBind();
    }

    @Override
    protected final void respond(final AjaxRequestTarget target) {
        new AjaxMediator(getComponent(), getEvent(), target, new Runnable() {
            @Override public void run()  {
                respond(getComponent(), getEvent(), target);
            }
        }).run();
    }

    protected abstract void respond(Component component, Object event, AjaxRequestTarget target);

    public Object getEvent() {
        return event;
    }




}
