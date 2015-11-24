package forms;

public class FormData {
    private String formId;
    private String formName;

    public FormData(WorkflowForm form) {
        this.formId = form.getMarkupId();
        this.formName = form.getFormConfig().getName();
    }

}

