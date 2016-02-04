package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class DemoState2 extends WfFormState {

    private @Inject DemoState3 demoState3;

    public DemoState2() {
        super(new Demo2FormConfig());
    }

    @Override
    @Nonnull public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return demoState3;
        }
        return unhandledEvent(workflow,event);
    }
}
