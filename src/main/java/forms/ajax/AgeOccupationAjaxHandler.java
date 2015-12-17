package forms.ajax;

import forms.WfAjaxHandler;
import forms.WorkflowForm;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public class AgeOccupationAjaxHandler extends WfAjaxHandler {

    public AgeOccupationAjaxHandler() {
        super("inputchange change");
    }

    public AgeOccupationAjaxHandler(String event) {
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
        String value = age>=65 ? "retired" : "youngin";
        Component occupation = workflowForm.getWfComponent("insured.occupation");
        IModel<String> model = (IModel<String>) occupation.getDefaultModel();
        model.setObject(value);
        target.add(occupation);
    }

}
