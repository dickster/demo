package forms.impl;

import forms.*;
import forms.model.GenericInsuranceObject;
import forms.spring.WfNavigator;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class DemoState6 extends WfFormState {

    private @Inject DemoState3 demoState3;
    private @Inject WfNavigator wfNavigator;

    public DemoState6() {
        super();
    }

    @Override
    public void onEnter(Workflow workflow) {
        DemoWorkflow wf = (DemoWorkflow) workflow;

        GenericInsuranceObject data = wf.getObject();
        String paymentMethod = data.getPayment().method;

        formConfig = "Credit Card".equalsIgnoreCase(paymentMethod) ?
            new Demo6AFormConfig() :
            new Demo6BFormConfig();
    }

    @Override
    @Nonnull public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return demoState3;
        }
        if (event.is("cancel")) {
            workflow.end();
        }
        return unhandledEvent(workflow,event);
    }
}
