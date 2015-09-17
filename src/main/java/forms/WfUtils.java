package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.protocol.http.WebSession;

public class WfUtils {

    public static Workflow<?> getCurrentWorkflow() {
        return ((BvSession)WebSession.get()).getWorkflow();
    }

    public static FormBasedWorkflow getFormBasedWorkflow() {
        Workflow<?> workflow = ((BvSession) WebSession.get()).getWorkflow();
        Preconditions.checkState(workflow instanceof FormBasedWorkflow);
        return (FormBasedWorkflow) workflow;
    }

    public static void setCurrentWorkflow(Workflow workflow) {
        ((BvSession)WebSession.get()).setWorkflow(workflow);
    }

}
