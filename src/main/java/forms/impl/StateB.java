package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class StateB  extends WfFormState {
    private @Inject @Named("stateC") WfFormState stateC;

    public StateB() {
        super(new FormBConfig());
    }

    @Nonnull
    @Override
    public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if ("next".equals(event.getName())) {
            return stateC;
        }
        return this;
    }

}

