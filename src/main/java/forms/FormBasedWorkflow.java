package forms;


import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import forms.config.FormConfig;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;

import javax.annotation.Nonnull;

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

    @Subscribe
    public void error(@Nonnull WfSubmitErrorEvent event) throws WorkflowException {
        WorkflowForm form = event.getForm().findParent(WorkflowForm.class);
        form.handleError(event);
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
