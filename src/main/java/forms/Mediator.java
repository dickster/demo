package forms;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Mediator {

    // when do i call mediator? before or after behavior is called.
    public enum MediatorType { VOID, BEFORE, AFTER };

    public static final String ABSTRACT_EVENT = "$ABSTRACT_EVENT$";

    private static void mediate(AbstractAjaxBehavior behavior, String event, AjaxRequestTarget target, Component component, List<MediatorType> callbacks) {
        Workflow<?> workflow = getWorkflow(component);
        WfAjaxEvent e = new WfAjaxEvent(event, target, component);
        if (callbacks.contains(MediatorType.BEFORE)) {
            workflow.post(e);
            if (e.isStopped()) {  // allow BEFORE callbacks chance to veto event.
                return;
            }
        }

        callSuperOnEventMethod(behavior, target);

        if (callbacks.contains(MediatorType.AFTER)) {
            workflow.post(e);
        }
    }

    private static @Nonnull Workflow<?> getWorkflow(Component component) {
        WorkflowPage parent = component.findParent(WorkflowPage.class);
        if (parent==null) {
            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
        }
        return parent.getWorkflow();
    }

    public static void mediate(AjaxEventBehavior behavior, AjaxRequestTarget target, Component component, List<MediatorType> callbacks) {
        mediate(behavior, behavior.getEvent(), target, component, callbacks);
    }

    public static void mediate(AbstractDefaultAjaxBehavior behavior, AjaxRequestTarget target, Component component, List<MediatorType> callbacks) {
        mediate(behavior, ABSTRACT_EVENT, target, component, callbacks);
    }

    private static void callSuperOnEventMethod(AbstractAjaxBehavior behavior, AjaxRequestTarget target) {
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

}

