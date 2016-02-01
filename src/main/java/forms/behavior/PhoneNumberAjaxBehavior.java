package forms.behavior;

import forms.WorkflowForm;
import forms.spring.WfAjaxBehavior;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

public class PhoneNumberAjaxBehavior extends WfAjaxBehavior {

    public PhoneNumberAjaxBehavior() {
        super();
    }

    public PhoneNumberAjaxBehavior(String event) {
        super(event);
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        WorkflowForm workflowForm = getWorkflowForm();
        String value = getComponent().getDefaultModelObjectAsString();
        boolean visible = value!=null && value.trim().contains("(416)");
        Component phoneCoverage = workflowForm.getWfComponent("insured.smokes");
        phoneCoverage.setVisible(visible);
        target.add(phoneCoverage);
    }

}
