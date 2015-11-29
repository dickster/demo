package forms.impl;

import forms.WfAjaxEvent;
import forms.WfAjaxHandler;
import forms.WorkflowForm;
import forms.util.WfAjaxEventPropagation;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;

public class AgeOccupationAjaxHandler implements WfAjaxHandler {

    private @Inject WfUtil wfUtil;

    @Override
    public WfAjaxEventPropagation handleAjax(WfAjaxEvent event) {

        if ("insured.age".equals(wfUtil.getComponentName(event.getComponent()))) {
            WorkflowForm workflowForm = wfUtil.getWorkflowForm(event.getComponent());
            int age = 0;
            try {
                 age = Integer.parseInt(event.getComponent().getDefaultModelObjectAsString());
            } catch (NumberFormatException e) {
                ; // don't do anything. it's not a number.
            }
            String value = age>=65 ? "retired" : "youngin";
            Component component = workflowForm.getComponent("insured.occupation");
            IModel<String> model = (IModel<String>) component.getDefaultModel();
            model.setObject(value);
            event.getTarget().add(component);
        }
        return WfAjaxEventPropagation.CONTINUE;
    }
}
