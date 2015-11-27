package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.WfState;
import forms.Workflow;

import javax.inject.Inject;
import javax.inject.Named;

public class StateAx extends WfFormState{

    private @Inject @Named("stateAy") WfFormState stateAy;

    public StateAx() {
        super(new FormANewLayoutConfig());
    }

    @Override
    public WfState handleEvent(Workflow workflow, WfEvent event) {
        if ("next".equals(event.getName())) {
            return stateAy;
        }
        return this;
    }
}

