package forms;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;

public class FormBasedWorkflowContext extends DefaultWorkflowContext {

    private MarkupContainer container;
    private boolean ajaxEnabled = false;
    private Component progress;

    public FormBasedWorkflowContext(MarkupContainer container) {
        super();
        this.container = container;
    }

    public MarkupContainer getContainer() {
        return container;
    }

    public FormBasedWorkflowContext withAjaxEnabled() {
        ajaxEnabled = true;
        return this;
    }
}
