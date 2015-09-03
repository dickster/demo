package forms;

import org.apache.wicket.MarkupContainer;

public class FormBasedWorkflowContext extends DefaultWorkflowContext {

    private MarkupContainer container;

    public FormBasedWorkflowContext(MarkupContainer container) {
        super();
        this.container = container;
    }

    public MarkupContainer getContainer() {
        return container;
    }
}
