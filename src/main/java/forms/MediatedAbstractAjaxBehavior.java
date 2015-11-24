package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.EnumSet;

// if you want to broadcast events to a parent mediator.
public abstract class MediatedAbstractAjaxBehavior extends AbstractDefaultAjaxBehavior {

    public static final String ABSTRACT_EVENT = "$ABSTRACT_EVENT$";

    private EnumSet<Advice> advice = EnumSet.of(Advice.BEFORE);

    public MediatedAbstractAjaxBehavior() {
        super();
    }

    public MediatedAbstractAjaxBehavior withTypes(Advice... advices) {
        Preconditions.checkArgument(advices.length > 0, "mediator must be called either before and/or after method");
        advice = EnumSet.noneOf(Advice.class);
        for (Advice a:advices) {
            advice.add(a);
        }
        return this;
    }

    @Override
    protected void respond(final AjaxRequestTarget target) {
        WfAjaxEvent event = new WfAjaxEvent(ABSTRACT_EVENT, target, getComponent());
        new Mediator().mediate(event, advice, new Runnable() {
            @Override public void run() {
                handleEvent(target);
            }
        });
    }

    protected void handleEvent(AjaxRequestTarget target) {
        // override this to do something if you want.  typically you will just want mediator
        // to handle all the biz logic methinks.
    }


}
