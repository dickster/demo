package forms.ajax;

import forms.spring.WfAjaxBehavior;
import forms.WorkflowForm;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public class AgeOccupationAjaxBehavior extends WfAjaxBehavior {

    public AgeOccupationAjaxBehavior() {
        super("inputchange change");
    }

    public AgeOccupationAjaxBehavior(String event) {
        super(event);
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        WorkflowForm workflowForm = getWorkflowForm();

        int age = 0;
        try {
             age = Integer.parseInt(getComponent().getDefaultModelObjectAsString());
        } catch (NumberFormatException e) {
            ; // don't do anything. it's not a number.
        }
        String value = age>=65 ? "senior" :
                age > 18 ? "adult" : "youth";
        Component occupation = workflowForm.getWfComponent("insured.occupation");
        IModel<String> model = (IModel<String>) occupation.getDefaultModel();
        model.setObject(value);
        // only update if model changes.
        target.add(occupation);
    }

}
