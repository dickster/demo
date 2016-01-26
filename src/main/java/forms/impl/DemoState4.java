package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class DemoState4 extends WfFormState {

    private @Inject DemoState2 demoState2;

    public DemoState4() {
        super(new Demo1FormConfig());
    }

    @Override
    @Nonnull public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return demoState2;
        }
        return unhandledEvent(workflow,event);
    }
}
