package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class DemoState5 extends WfFormState {

    private @Inject DemoState3 demoState3;

    public DemoState5() {
        super(new Demo5FormConfig());
    }

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
