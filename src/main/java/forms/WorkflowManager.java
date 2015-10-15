package forms;

import org.apache.wicket.markup.html.WebPage;

public class WorkflowManager {

    private final FormBasedWorkflow workflow;
    private final WebPage page;
    private final String formId;

    public WorkflowManager(FormBasedWorkflow workflow, WebPage page, String id) {
        this.workflow = workflow;
        this.page = page;
        this.formId = id;
    }

    public DynamicForm addOrReplaceForm() {
        DynamicForm form = new DynamicForm(formId, workflow.getCurrentFormConfig());
        this.page.addOrReplace(form);
        return form;
    }

    public FormBasedWorkflow getWorkflow() {
        return workflow;
    }
}
