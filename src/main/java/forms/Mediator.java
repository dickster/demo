package forms;

import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class Mediator {

    // TODO : make this a spring bean.
    public void mediate(WfAjaxEvent event, EnumSet<Advice> advice, Runnable onEvent) {

        Workflow workflow = getWorkflow(event.getComponent());
        if (advice.contains(Advice.BEFORE)) {
            workflow.post(event);
            if (event.isStopped()) {  // allow BEFORE callbacks chance to veto event.
                return;
            }
        }

        onEvent.run();

        if (advice.contains(Advice.AFTER)) {
            workflow.post(event);
        }
    }

    protected final @Nonnull Workflow getWorkflow(@Nonnull Component component) {
        HasWorkflow parent = component.findParent(HasWorkflow.class);
        if (parent==null) {
            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
        }
        return parent.getWorkflow();
    }

}
