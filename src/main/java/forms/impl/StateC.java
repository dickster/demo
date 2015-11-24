package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.WfState;
import forms.Workflow;
import forms.config.FormCConfig;

public class StateC extends WfFormState {
    public StateC() {
        super(new FormCConfig());
    }

    @Override
    public WfState handleEvent(Workflow workflow, WfEvent event) {
        if ("ok".equals(event.getName())) {
            workflow.end();
        }
        return this;
    }
}

