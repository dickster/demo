package forms;

import forms.util.WfUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

public abstract class WfAjaxBehavior extends AjaxFormComponentUpdatingBehavior {

    public WfAjaxBehavior(String event) {
        super(event);
    }

    @Override
    protected void onUpdate(final AjaxRequestTarget target) {
        postAjaxEvent(target);
    }

    protected final void postAjaxEvent(AjaxRequestTarget target) {
        Workflow workflow = WfUtil.getWorkflow(getComponent());
        workflow.post(createEvent(target));
    }

    protected WfAjaxEvent createEvent(AjaxRequestTarget target) {
        return new WfAjaxEvent(getEvent(), target, getComponent());
    }

    protected WorkflowForm getWorkflowForm() {
        return WfUtil.getWorkflowForm(getComponent());
    }
}
