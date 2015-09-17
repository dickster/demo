package forms;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;

public class FormBasedWorkflow extends Workflow<FormBasedWorkflowContext> {

    private boolean useAjax = false;

    public FormBasedWorkflow() {
        super(new FormBasedWorkflowContext());
    }

    public FormConfig getCurrentFormConfig() {
        Preconditions.checkState(currentState instanceof WfFormState);
        return ((WfFormState)currentState).getFormConfig();
    }

    @Override
    protected void stateChange(WfState state, WfEvent event) {
        //Preconditions.checkState(state instanceof WfFormState);
        super.stateChange(state, event);
        WfFormState fs = (WfFormState)state;
        if (useAjax) {  // setREsponsePage(this);  page...
            updateForm(fs, event);
        } else {   // target.addOrReplace(new DynamicForm(oldFormId, workflow.formConfig))
            changePage(fs, event);
        }
    }

    @Subscribe
    public void changePage(WfFormState state, WfEvent event) {
        //setResponsePage(this);
    }

    @Subscribe
    public void updateForm(WfFormState state, WfEvent event) {
        // need ajax goodies. target, WfFormState via workflow.
        Form form = event.getForm();
        addOrReplaceForm(new DynamicForm(form.getId(), ));
        event.getTarget().add(form);
    }




    @Override
    protected void enteringAsyncState(WfState state, WfEvent event) {
        super.enteringAsyncState(state, event);
        post(new WfProgressEvent(state,event,0));
    }

    @Override
    protected void leavingAysncState(WfState state, WfEvent event) {
        super.leavingAysncState(state, event);
        page.endProgress(state);
    }
}
