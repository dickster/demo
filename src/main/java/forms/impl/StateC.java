package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

public class StateC extends WfFormState {
    public StateC() {
        super(new FormCConfig());
    }

    @Override
    public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if ("ok".equals(event.getName())) {
            workflow.end();
        }
        return this;
    }
}

