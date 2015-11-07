package forms;


import com.google.common.base.Preconditions;
import forms.config.FormConfig;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;

public abstract class FormBasedWorkflow<T> extends Workflow<T> {

    public FormBasedWorkflow() {
        super();
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

        // need to show form.if ajax
        if (event instanceof WfAjaxEvent) {
            updateFormViaAjax((WfAjaxEvent)event);
        }
        else {
            updatePage();
        }
    }

    protected void updateFormViaAjax(WfAjaxEvent event) {
        DynamicForm form = event.getComponent().findParent(DynamicForm.class);
        if (form!=null) {
            DynamicForm newForm = new DynamicForm(form.getId(), getCurrentFormConfig());
            form.replaceWith(newForm);
            event.getTarget().add(newForm);
        }
    }

    protected void updatePage() {
        RequestCycle requestCycle = RequestCycle.get();
        if (requestCycle!=null) {
            requestCycle.setResponsePage(createResponsePage());
        } else {
            System.out.println("your workflow is redirecting to a page without a request cycle?  huh???");
        }
    }

    protected WebPage createResponsePage() {
        return new WfPage(this);
    }

}
