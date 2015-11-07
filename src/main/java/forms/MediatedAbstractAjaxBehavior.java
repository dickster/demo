package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.EnumSet;

// if you want to broadcast events to a parent mediator.
public abstract class MediatedAbstractAjaxBehavior extends AbstractDefaultAjaxBehavior {

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
    protected final void respond(final AjaxRequestTarget target) {
        new WorkflowManager().mediate(this, target, getComponent(), advice);
    }

}
