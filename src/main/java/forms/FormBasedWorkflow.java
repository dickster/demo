package forms;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import forms.config.FormConfig;
import forms.util.WfAjaxEventPropagation;
import forms.util.WfUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

public abstract class FormBasedWorkflow<T> extends Workflow<T, WfFormState> {

    private List<WfAjaxHandler> ajaxHandlers;
    private @Inject WfUtil wfUtil;

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
    public void debug(@Nonnull WfDebugEvent event) {
        refreshForm(event);
    }

    private void refreshForm(WfDebugEvent event) {
        WorkflowForm form = event.getComponent().findParent(WorkflowForm.class);
        updateFormViaAjax(form, event.getTarget());
    }


    @Override
    protected void changeState(WfFormState nextState, WfSubmitEvent event) {
        if (event instanceof WfSubmitErrorEvent && getCurrentState().equals(nextState)) {
            // do you want to stay on this page? we'll notify form so you can add
            //  to the ajax target.
            updateErrorViaAjax((WfSubmitErrorEvent) event);
            return;
        }
        changeState(nextState);
        updateFormViaAjax(event);
    }

    private void updateErrorViaAjax(WfSubmitErrorEvent event) {
        WorkflowForm form = event.getForm().findParent(WorkflowForm.class);
        form.handleError(event);
    }

    protected void updateFormViaAjax(WfSubmitEvent event) {
        WorkflowForm form = event.getForm().findParent(WorkflowForm.class);
        updateFormViaAjax(form, event.getTarget());
        event.getTarget().appendJavaScript("workflow.pushHistory();");
    }

    protected void updateFormViaAjax(WorkflowForm form, AjaxRequestTarget target) {
        WorkflowForm newForm = createForm(form.getId(), getCurrentFormConfig());
        form.replaceWith(newForm);
        target.add(newForm);
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

    public void gotoState(String state, AjaxRequestTarget target, WorkflowForm form) {
        // note that we don't allow user to go to states that he hasn't been in.
        // that means if you paste in a history generated url after session expires you will probably bomb here....
        // not sure how to handle it gracefully...just go to starting state? or any visitedState?
        Preconditions.checkState(statesVisited.get(state) != null, " can't go to state " + state + " because you haven't been to it yet? {" + statesVisited.keySet() + "}");
        super.changeState(statesVisited.get(state));
        updateFormViaAjax(form, target);
    }

}
