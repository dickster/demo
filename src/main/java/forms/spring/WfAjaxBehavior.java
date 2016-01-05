package forms.spring;

import forms.WorkflowForm;
import forms.util.WfUtil;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

public abstract class WfAjaxBehavior extends AjaxFormComponentUpdatingBehavior {

    public WfAjaxBehavior() {
        super("change");
    }

    protected WfAjaxBehavior(String event) {
        super(event);
    }

    protected WorkflowForm getWorkflowForm() {
        return WfUtil.getWorkflowForm(getComponent());
    }

}
