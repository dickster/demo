package forms;

import org.apache.wicket.MarkupContainer;

public class DynamicFormManager {

    public DynamicForm addOrReplaceForm(MarkupContainer container) {
        FormBasedWorkflow workflow = WfUtils.getFormBasedWorkflow();
        FormConfig formConfig = workflow.getCurrentFormConfig();
        DynamicForm form = new DynamicForm("form", formConfig);
        container.addOrReplace(form);
        return form;
    }

    public void startProgress(WfState state) {
        //TODO : whatever you want...show spinner, dialog, etc...
    }

    public void endProgress(WfState state) {
        //TODO : whatever you want...show spinner, dialog, etc...
    }

}
