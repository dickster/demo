package forms.impl;

import forms.*;
import forms.model.GenericInsuranceObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class DemoState5 extends WfFormState /*<W>*/ {

    private @Inject DemoState6 demoState6;

    public DemoState5() {
        super(new Demo5FormConfig());
    }

//    @Override
//    public void enter(/*<W>*/Workflow workflow, WorkflowForm workflowForm) {
//        DemoWorkflow wf = (DemoWorkflow) workflow;
//
//        GenericInsuranceObject data = wf.getObject();
//        String phoneNumber = data.getInsured().contact.phone;
//        String email = data.getInsured().contact.email;
//
//        Component notify = workflowForm.getWfComponent("insured.notifyMe");
//        notify.setVisible(StringUtils.isNotBlank(email));
//
//        Component _416 = workflowForm.getWfComponent("insured.smokes");
//        _416.setVisible(StringUtils.isNotBlank(phoneNumber));
//    }

    @Override
    @Nonnull public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return demoState6;
        }
        return unhandledEvent(workflow,event);
    }
}
