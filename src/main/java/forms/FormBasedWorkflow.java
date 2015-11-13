package forms;


import com.google.common.base.Preconditions;
import forms.config.FormConfig;

public abstract class FormBasedWorkflow<T> extends Workflow<T> {

    public FormBasedWorkflow() {
    }

    public FormConfig getCurrentFormConfig() {
        Preconditions.checkState(currentState instanceof WfFormState);
        return ((WfFormState)currentState).getFormConfig();
    }

    @Override
    protected void validate(WfState nextState) {
        super.validate(nextState);
        Preconditions.checkArgument(nextState instanceof WfFormState, "workflow states must be of type " + WfFormState.class.getSimpleName());
    }

    @Override
    protected void changeState(WfState nextState, WfEvent event) {
        super.changeState(nextState, event);
        if (event instanceof WfAjaxEvent) {
            updateFormViaAjax((WfAjaxEvent)event);
        }
    }

    protected void updateFormViaAjax(WfAjaxEvent event) {
        WorkflowForm form = event.getComponent().findParent(WorkflowForm.class);
        if (form!=null) {
            WorkflowForm newForm = createForm(form.getId(), getCurrentFormConfig());
            form.replaceWith(newForm);
            event.getTarget().add(newForm);
        }
    }

    public WorkflowForm createForm(String id, FormConfig config) {
        return new WorkflowForm(id, config).withModel(getModel());
    }

}
