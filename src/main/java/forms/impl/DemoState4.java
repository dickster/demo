package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class DemoState4 extends WfFormState {

    private @Inject DemoState5 demoState5;

    public DemoState4() {
        super(new Demo4FormConfig());
    }

    @Override
    @Nonnull public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return demoState5;
        }
        if (event.is("cancel")) {
            workflow.end();
            return this;
        }
        else {
            return unhandledEvent(workflow,event);
        }
    }
}
