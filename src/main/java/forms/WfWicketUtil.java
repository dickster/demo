package forms;


import org.apache.wicket.Component;

import javax.annotation.Nonnull;

// wicket/workflow integration util.
public class WfWicketUtil {

    public static void post(@Nonnull Component component, @Nonnull WfEvent event) {
        Workflow<?> workflow = getWorkflow(component);
        workflow.post(event);
    }

    private static @Nonnull Workflow<?> getWorkflow(@Nonnull Component component) {
        WorkflowPage parent = component.findParent(WorkflowPage.class);
        if (parent==null) {
            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
        }
        return parent.getWorkflow();
    }

}

