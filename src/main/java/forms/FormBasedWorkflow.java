package forms;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;

public class FormBasedWorkflow extends Workflow<FormBasedWorkflowContext> {

    private static WfState e = new WfState() {};

    private boolean useAjax = false;

    public FormBasedWorkflow(MarkupContainer container) {
        super(e, new FormBasedWorkflowContext(container));
    }

    // alias this to "show" or "displayForm"
    public void replace(Component c) {
        getContext().getContainer().replace(c);
    }

    public void add(Component c) {
        getContext().getContainer().add(c);
    }

}
