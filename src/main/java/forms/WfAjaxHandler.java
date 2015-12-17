package forms;

import forms.util.WfUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;

// TODO : This behavior does not work on Choices or Groups. need to allow for that.
// will need to refactor an interface and allow for two types of behaviors.
@Scope("prototype")
public abstract class WfAjaxHandler extends AjaxFormComponentUpdatingBehavior {

    @Inject
    protected WfUtil wfUtil;

    public WfAjaxHandler(String event) {
        super(event);
    }

    @Override
    protected void onUpdate(final AjaxRequestTarget target) {
    }

    protected WorkflowForm getWorkflowForm() {
        return wfUtil.getWorkflowForm(getComponent());
    }
}
