package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.EnumSet;



public class MediatedAjaxEventBehavior extends AjaxEventBehavior{

    private EnumSet<Advice> advice = EnumSet.of(Advice.BEFORE);

    public MediatedAjaxEventBehavior(String event) {
        super(event);
    }

    public MediatedAjaxEventBehavior withTypes(Advice... advices) {
        Preconditions.checkArgument(advices.length > 0, "mediator must be called either before and/or after method");
        advice = EnumSet.noneOf(Advice.class);
        for (Advice a:advices) {
            advice.add(a);
        }
        return this;
    }


    @Override
    protected void onEvent(AjaxRequestTarget target) {
        new WorkflowManager().mediate(this, target, getComponent(), advice);
    }

}
