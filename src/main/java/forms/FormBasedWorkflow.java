package forms;


import com.google.common.collect.Lists;
import forms.config.FormConfig;
import forms.util.WfAjaxEventPropagation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class FormBasedWorkflow<T> extends Workflow<T, WfFormState> {

    private List<WfAjaxHandler> ajaxHandlers;

    public FormBasedWorkflow() {
        super();
    }

    public FormConfig getCurrentFormConfig() {
        return getCurrentState().getFormConfig();
    }

    @Override
    protected void validate(WfFormState nextState) {
        super.validate(nextState);
    }

    @Override
    public void handleAjaxEvent(@Nonnull WfAjaxEvent event) throws WorkflowException {
        for (WfAjaxHandler handler:ajaxHandlers) {
            if (WfAjaxEventPropagation.STOP.equals(handler.handleAjax(event))) {
                return;
            }
        }
        getCurrentState().handleAjaxEvent(event);
    }

    @Override
    protected void changeState(WfFormState nextState, WfSubmitEvent event) {
        if (event instanceof WfSubmitErrorEvent && getCurrentState().equals(nextState)) {
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
        return new WorkflowForm(id, config);
    }

    public List<WfAjaxHandler> getAjaxHandlers() {
        return Lists.newArrayList(ajaxHandlers);
    }

    public FormBasedWorkflow<T> withAjaxHandlers(WfAjaxHandler... handlers) {
        ajaxHandlers = Lists.newArrayList(handlers);
        return this;
    }
}
