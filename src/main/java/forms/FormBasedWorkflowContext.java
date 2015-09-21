package forms;

public class FormBasedWorkflowContext extends DefaultWorkflowContext {

    private boolean ajaxEnabled = false;

    public FormBasedWorkflowContext() {
        super();
    }

    public FormBasedWorkflowContext withAjaxEnabled() {
        ajaxEnabled = true;
        return this;
    }
}
