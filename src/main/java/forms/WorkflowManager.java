package forms;

import org.apache.wicket.markup.html.WebPage;

public class WorkflowManager {

    private final FormBasedWorkflow workflow;
    private final WebPage page;

    public WorkflowManager(FormBasedWorkflow workflow, WebPage page) {
        this.workflow = workflow;
        this.page = page;
    }

    public void startProgress(WfState state) {
        //TODO : whatever you want...show spinner, dialog, etc...
    }

    public void endProgress(WfState state) {
        //TODO : whatever you want...show spinner, dialog, etc...
    }

    public DynamicForm addOrReplaceForm() {
        DynamicForm form = new DynamicForm("form", workflow.getCurrentFormConfig());
        this.page.addOrReplace(form);
        return form;
    }

    public FormBasedWorkflow getWorkflow() {
        return workflow;
    }
}
