package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.WfState;
import forms.Workflow;
import forms.config.FormBConfig;

import javax.inject.Inject;
import javax.inject.Named;

public class StateB  extends WfFormState {
    private @Inject @Named("stateC") WfFormState stateC;

    public StateB() {
        super(new FormBConfig());
    }

    @Override
    public WfState handleEvent(Workflow workflow, WfEvent event) {
        if ("next".equals(event.getName())) {
            return stateC;
        }
        return this;
    }

}

