package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

import java.util.EnumSet;



// TODO : This behavior does not work on Choices or Groups. need to allow for that.
// will need to refactor an interface and allow for two types of behaviors.

@Deprecated
public class MediatedAjaxEventBehavior extends AjaxFormComponentUpdatingBehavior {

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
    protected void onUpdate(final AjaxRequestTarget target) {
        WfAjaxEvent event = new WfAjaxEvent(getEvent(), target, getComponent());
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
