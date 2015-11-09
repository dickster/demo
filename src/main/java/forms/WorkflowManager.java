package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;

public class WorkflowManager {

    public static final String ABSTRACT_EVENT = "$ABSTRACT_EVENT$";

    public WorkflowManager() {
    }

    public void mediate(AjaxEventBehavior behavior, AjaxRequestTarget target, Component component, EnumSet<Advice> advice) {
        mediate(behavior, behavior.getEvent(), target, component, advice);
    }

    public void mediate(AbstractDefaultAjaxBehavior behavior, AjaxRequestTarget target, Component component, EnumSet<Advice> advice) {
        mediate(behavior, ABSTRACT_EVENT, target, component, advice);
    }

    private void mediate(AbstractAjaxBehavior behavior, String event, AjaxRequestTarget target, Component component, EnumSet callbacks) {
        Workflow<?> workflow = getWorkflow(component);
        WfAjaxEvent e = new WfAjaxEvent(event, target, component);
        if (callbacks.contains(Advice.BEFORE)) {
            workflow.post(e);
            if (e.isStopped()) {  // allow BEFORE callbacks chance to veto event.
                return;
            }
        }

        callSuperOnEventMethod(behavior, target);

        if (callbacks.contains(Advice.AFTER)) {
            workflow.post(e);
        }
    }

    private void callSuperOnEventMethod(AbstractAjaxBehavior behavior, AjaxRequestTarget target) {
        // blargh.  super.onEvent can't be called directly so i have to call it via reflection.
        // this equates to super.onEvent(target);
        try {
            // TODO : i should cache this. store in Map<Class, Method>
            Method m = behavior.getClass().getDeclaredMethod("onEvent", AjaxRequestTarget.class);
            m.invoke(behavior, target);
        } catch (NoSuchMethodException e) {
            // oops.  seriously screwed if this happens.
            System.out.println("????");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void post(@Nonnull Component component, @Nonnull WfEvent event) {
        Workflow<?> workflow = getWorkflow(component);
        workflow.post(event);
    }

    private @Nonnull Workflow<?> getWorkflow(@Nonnull Component component) {
        HasWorkflow parent = component.findParent(HasWorkflow.class);
        if (parent==null) {
            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
        }
        return parent.getWorkflow();
    }
}
