package forms;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.WebPage;

public class WfPage extends WebPage implements HasWorkflow, IAjaxIndicatorAware {

    private FormBasedWorkflow workflow;

    public WfPage(FormBasedWorkflow workflow) {
        super();
        this.workflow = workflow;
        add( new WorkflowForm("form", workflow.getCurrentFormConfig())
                .withModel(workflow.getModel())
        );
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




