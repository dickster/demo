package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class StateAy extends WfFormState {

    private @Inject @Named("stateB") WfFormState stateB;
    private @Inject @Named("stateError") WfFormState stateError;

    public StateAy() {
        super(new FormAAnotherNewLayoutConfig());
    }

    @Override
    public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if ("next".equals(event.getName())) {
            return stateB;
        }
        return this;
    }

    @Nonnull
    @Override
    public WfState handleError(Workflow workflow, WfSubmitEvent event) {
        return stateError;
    }
}

