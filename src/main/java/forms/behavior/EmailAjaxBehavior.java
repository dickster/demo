package forms.behavior;

import forms.WorkflowForm;
import forms.spring.WfAjaxBehavior;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public class EmailAjaxBehavior extends WfAjaxBehavior {

    public EmailAjaxBehavior() {
        super("inputchange");
    }

    public EmailAjaxBehavior(String event) {
        super(event);
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        WorkflowForm workflowForm = getWorkflowForm();
        boolean visible = StringUtils.isNotBlank(getComponent().getDefaultModelObjectAsString());
        Component notifyMe = workflowForm.getWfComponent("insured.notifyMe");
        notifyMe.setVisible(visible);
    }

}
