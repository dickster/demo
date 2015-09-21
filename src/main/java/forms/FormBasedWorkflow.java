package forms;


import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class FormBasedWorkflow extends Workflow<FormBasedWorkflowContext> {

    private boolean useAjax = true;

    public FormBasedWorkflow() {
        super(new FormBasedWorkflowContext());
    }

    public FormConfig getCurrentFormConfig() {
        Preconditions.checkState(currentState instanceof WfFormState);
        return ((WfFormState)currentState).getFormConfig();
    }

    @Subscribe
    public void changeState(WfChangeStateEvent event) {
        WfEvent e = event.getTriggerEvent();
        WfState state = event.getState();


            // need to show form.if ajax
            if (e instanceof WfAjaxEvent) {
                //blah...
                // show form associated with page thru ajax;
            }
            else {
                RequestCycle requestCycle = RequestCycle.get();
                if (requestCycle!=null) {
                    requestCycle.setResponsePage(createResponsePage());
                } else {
                    System.out.println("your workflow is redirecting to a page without a request cycle?  huh???");
                }
            }

    }

    protected WebPage createResponsePage() {
        return new WfPage(new PageParameters());
    }

}
