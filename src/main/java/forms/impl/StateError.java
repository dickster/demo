package forms.impl;

import forms.WfFormState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.inject.Inject;
import javax.inject.Named;

public class StateError extends WfFormState {

    private @Inject @Named("stateA") WfFormState stateA;

    public StateError() {
        super(new FormErrorConfig());
    }

    @Override
    public WfFormState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if ("ok".equals(event.getName())) {
            return stateA;
        }
        return this;
    }
}
