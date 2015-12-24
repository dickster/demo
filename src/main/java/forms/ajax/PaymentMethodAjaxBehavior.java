package forms.ajax;

import forms.spring.WfAjaxBehavior;
import forms.WorkflowForm;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class PaymentMethodAjaxBehavior extends WfAjaxBehavior {

    public PaymentMethodAjaxBehavior() {
        super("change inputchange");
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        String paymentMethod = getComponent().getDefaultModelObjectAsString();
        boolean visible = "Credit Card".equalsIgnoreCase(paymentMethod);
        WorkflowForm form = getWorkflowForm();
        target.add(form.getWfComponent("creditCardDiv").setVisible(visible));
    }

}
