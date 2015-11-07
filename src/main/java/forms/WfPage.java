package forms;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.WebPage;

public class WfPage extends WebPage implements HasWorkflow, IAjaxIndicatorAware {

    private WorkflowManager workflowManager = new WorkflowManager();
    private FormBasedWorkflow workflow;
    private DynamicForm form;

    public WfPage(FormBasedWorkflow workflow) {
        super();
        this.workflow = workflow;
        form = workflowManager.addOrReplaceForm("form", this);
    }

    @Override
    public Workflow<?> getWorkflow() {
        return workflow;
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return "progress";
    }
}




