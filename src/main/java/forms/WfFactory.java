package forms;

public class WfFactory {

    public Workflow create(String workflowType) {
        return new CommercialWorkflow( new FormBasedWorkflowContext<TestData>() );
    }

}
