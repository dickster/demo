package forms;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.WebPage;

public class WfPage extends WebPage implements WorkflowPage, IAjaxIndicatorAware {

    private WorkflowManager workflowManager;
    private DynamicForm form;

    public WfPage(FormBasedWorkflow workflow) {
        super();
        workflowManager = new WorkflowManager(workflow, this, "form");
        form = workflowManager.addOrReplaceForm();
    }

    @Override
    public Workflow getWorkflow() {
        return workflowManager.getWorkflow();
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return "progress";
    }
}




