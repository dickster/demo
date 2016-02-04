package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;

public class StateC extends WfFormState {
    public StateC() {
        super(new FormCConfig());
    }

    @Nonnull
    @Override
    public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        if ("ok".equals(event.getName())) {
            workflow.end();
        }
        return this;
    }
}

