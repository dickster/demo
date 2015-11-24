package forms;

public class WorkflowManager {

    public WorkflowManager() {
    }

//    public void mediate(AjaxEventBehavior behavior, AjaxRequestTarget target, Component component, EnumSet<Advice> advice) {
//        mediate(behavior, behavior.getEvent(), target, component, advice);
//    }
//
//    public void mediate(AbstractDefaultAjaxBehavior behavior, AjaxRequestTarget target, Component component, EnumSet<Advice> advice) {
//        mediate(behavior, ABSTRACT_EVENT, target, component, advice);
//    }
//
//    private void mediate(AbstractAjaxBehavior behavior, String event, AjaxRequestTarget target, Component component, EnumSet callbacks) {
//        Workflow workflow = getWorkflow(component);
//        WfAjaxEvent e = new WfAjaxEvent(event, target, component);
//        if (callbacks.contains(Advice.BEFORE)) {
//            workflow.post(e);
//            if (e.isStopped()) {  // allow BEFORE callbacks chance to veto event.
//                return;
//            }
//        }
//
//        callSuperOnEventMethod(behavior, target);
//
//        if (callbacks.contains(Advice.AFTER)) {
//            workflow.post(e);
//        }
//    }
//
//    private void callSuperOnEventMethod(AbstractAjaxBehavior behavior, AjaxRequestTarget target) {
//        // blargh.  super.onEvent can't be called directly so i have to call it via reflection.
//        // this equates to super.onEvent(target);
//        try {
//            // TODO : i should cache this. store in Map<Class, Method>
//            Class wicketBehaviorClass = getSuperClass(behavior);
//            Method m = wicketBehaviorClass.getDeclaredMethod("onEvent", AjaxRequestTarget.class);
//            m.invoke(behavior, target);
//        } catch (NoSuchMethodException e) {
//            // oops.  seriously screwed if this happens.
//            System.out.println("????");
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//     }
//
//    // get the super class that is out of this framework.  (i.e. not a mediatedeventbehavior.)
//    private Class getSuperClass(AbstractAjaxBehavior behavior) {
//        // TODO : this doesn't check for people extending mediated behavior.
//        // i should validated that it is the parent of mediated behavior.
//        return behavior.getClass().getSuperclass();
//    }
//
//    protected final @Nonnull Workflow getWorkflow(@Nonnull Component component) {
//        HasWorkflow parent = component.findParent(HasWorkflow.class);
//        if (parent==null) {
//            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
//        }
//        return parent.getWorkflow();
//    }

}
