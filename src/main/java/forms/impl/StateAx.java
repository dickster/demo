package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class StateAx extends WfFormState{

    private @Inject @Named("stateAy") WfFormState stateAy;

    public StateAx() {
        super(new FormANewLayoutConfig());
    }

    @Nonnull
    @Override
    public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        if ("next".equals(event.getName())) {
            return stateAy;
        }
        return this;
    }
}

