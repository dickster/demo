package forms;

import com.sun.istack.internal.NotNull;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Mediator {

    public static final String ABSTRACT_EVENT = "$ABSTRACT_EVENT$";

    enum MediatorType { VOID, PRE, POST };


    private static void post(WfAjaxEvent event) {
        getWorkflowFromSession().post(event);
    }

    static private @NotNull DefaultWorkflow getWorkflowFromSession() {
        // TODO : read wicket session etc...
        return new DefaultWorkflow();
    }

    private static void mediate(AbstractAjaxBehavior behavior, String event, AjaxRequestTarget target, Component component, List<MediatorType> callbacks) {
        WfAjaxEvent e = new WfAjaxEvent(event, target, component).withType(MediatorType.PRE);
        if (callbacks.contains(MediatorType.PRE)) {
            post(e);
            if (e.isStopped()) {  // allow PRE callbacks chance to veto event.
                return;
            }
        }

        callSuperOnEventMethod(behavior, target);

        if (callbacks.contains(MediatorType.POST)) {
            post(e.withType(MediatorType.POST));
        }
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}

