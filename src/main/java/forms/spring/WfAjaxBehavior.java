package forms.spring;

import forms.WorkflowForm;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

import javax.inject.Inject;

public abstract class WfAjaxBehavior extends AjaxFormComponentUpdatingBehavior {

    private @Inject WfNavigator wfNavigator;

    public WfAjaxBehavior() {
        super("change");
    }

    protected WfAjaxBehavior(String event) {
        super(event);
    }

    protected WorkflowForm getWorkflowForm() {
        return wfNavigator.getWorkflowForm(getComponent());
    }

}
