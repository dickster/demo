package forms;

import org.apache.wicket.Component;

public class FormBasedWorkflowContext extends DefaultWorkflowContext {

    private boolean ajaxEnabled = false;
    private Component progress;

    public FormBasedWorkflowContext() {
        super();
    }

    public FormBasedWorkflowContext withAjaxEnabled() {
        ajaxEnabled = true;
        return this;
    }
}
