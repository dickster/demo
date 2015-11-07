package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;

import javax.annotation.Nonnull;

public class WorkflowManager {

    public WorkflowManager() {
    }

    public <T extends WebPage & HasWorkflow> DynamicForm addOrReplaceForm(String id, @Nonnull T page) {
        Preconditions.checkArgument(page.getWorkflow() instanceof FormBasedWorkflow);
        FormBasedWorkflow<?> workflow = (FormBasedWorkflow<?>) page.getWorkflow();
        DynamicForm form = new DynamicForm(id, workflow.getCurrentFormConfig());
        page.addOrReplace(form);
        return form;
    }

    public void post(@Nonnull Component component, @Nonnull WfEvent event) {
        Workflow<?> workflow = getWorkflow(component);
        workflow.post(event);
    }

    private @Nonnull Workflow<?> getWorkflow(@Nonnull Component component) {
        HasWorkflow parent = component.findParent(HasWorkflow.class);
        if (parent==null) {
            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
        }
        return parent.getWorkflow();
    }
}
