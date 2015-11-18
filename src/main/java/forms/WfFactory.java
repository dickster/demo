package forms;

public class WfFactory {

    public FormBasedWorkflow create(String workflowType) {
        return new CommercialWorkflow();
    }

}
