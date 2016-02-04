package forms.impl;

import forms.*;
import forms.model.GenericInsuranceObject;
import forms.spring.WfNavigator;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class DemoState6 extends WfFormState {

    private @Inject DemoState3 demoState3;
    private @Inject WfNavigator wfNavigator;

    public DemoState6() {
        super(new Demo6FormConfig());
    }

//    @Override
//    public void enter(Workflow workflow, WorkflowForm workflowForm) {
//        DemoWorkflow wf = (DemoWorkflow) workflow;
//
//        GenericInsuranceObject data = wf.getObject();
//        String paymentMethod = data.getPayment().method;
//
//        Component usesCC = workflowForm.getWfComponent("usesCC");
//        usesCC.setVisible("Credit Card".equalsIgnoreCase(paymentMethod));
//
//        Component usesCash = workflowForm.getWfComponent("usesCash");
//        usesCC.setVisible("Cash".equalsIgnoreCase(paymentMethod));
//    }

    @Override
    @Nonnull public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return demoState3;
        }
        if (event.is("cancel")) {
            workflow.end();
        }
        return unhandledEvent(workflow,event);
    }
}
