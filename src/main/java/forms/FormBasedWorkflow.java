package forms;


import com.google.common.base.Preconditions;
import forms.config.FormConfig;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;

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
    protected void changeState(WfState nextState, WfSubmitEvent event) {
        if (event instanceof WfSubmitErrorEvent && currentState.equals(nextState)) {
            // do you want to stay on this page? we'll notify form so you can add
            //  to the ajax target.
            updateErrorViaAjax((WfSubmitErrorEvent) event);
            return;
        }
        super.changeState(nextState, event);
        updateFormViaAjax(event);
    }

    private void updateErrorViaAjax(WfSubmitErrorEvent event) {
        WorkflowForm form = event.getForm().findParent(WorkflowForm.class);
        form.handleError(event);
    }

    protected void updateFormViaAjax(WfSubmitEvent event) {
        WorkflowForm form = event.getForm().findParent(WorkflowForm.class);
        if (form!=null) {
            WorkflowForm newForm = createForm(form.getId(), getCurrentFormConfig());
            form.replaceWith(newForm);
            event.getTarget().add(newForm);
        }
    }

    protected void updatePage(WebPage page) {
        RequestCycle requestCycle = RequestCycle.get();
        if (requestCycle!=null) {
            requestCycle.setResponsePage(page);
        } else {
            System.out.println("your workflow is redirecting to a page without a request cycle?  huh???");
        }
    }

    public WorkflowForm createForm(String id, FormConfig config) {
        return new WorkflowForm(id, config, getModel());
    }

}
