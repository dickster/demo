package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import forms.spring.WfAjaxBehavior;
import forms.widgets.config.FormConfig;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class FormBasedWorkflow<T> extends Workflow<T, WfFormState> {

    private List<WfAjaxBehavior> ajaxBehaviors;

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
    public void onDebug(@Nonnull WfDebugEvent event) {
        refreshForm(event);
    }

    private void refreshForm(WfEvent event) {
        WorkflowForm form = event.getComponent().findParent(WorkflowForm.class);
        updateFormViaAjax(form, event.getTarget());
    }

    @Override
    public void onValidation(WfValidationEvent event) {
    }

    @Override
    void onSubmitError(WfSubmitErrorEvent event) throws WorkflowException {

    }

    @Override
    protected void changeState(WfFormState nextState, WfSubmitEvent event) {
        if (changeState(nextState)) {
            updateFormViaAjax(event);
        }
    }

    protected void updateFormViaAjax(WfSubmitEvent event) {
        WorkflowForm form = event.getForm().findParent(WorkflowForm.class);
        updateFormViaAjax(form, event.getTarget());
        event.getTarget().appendJavaScript("wf.pushHistory();");
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

    public List<WfAjaxBehavior> getAjaxBehaviors() {
        return Lists.newArrayList(ajaxBehaviors);
    }

    public FormBasedWorkflow<T> withAjaxBehaviors(WfAjaxBehavior... behaviors) {
        ajaxBehaviors = Lists.newArrayList(behaviors);
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
