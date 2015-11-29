package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.Workflow;

import javax.inject.Inject;
import javax.inject.Named;

public class State1 extends WfFormState {

    private @Inject @Named("stateA") WfFormState stateA;

    public State1() {
        super(new Form1Config());
    }

    @Override
    public WfFormState handleEvent(Workflow workflow, WfEvent event) {
        if ("next".equals(event.getName())) {
            return stateA;
        }
        return this;
    }
}
