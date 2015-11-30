package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.Workflow;

import javax.inject.Inject;
import javax.inject.Named;

public class StateA extends WfFormState {

    private @Inject @Named("stateAx") WfFormState stateAx;
    private @Inject @Named("stateAy") WfFormState stateAy;
    private @Inject @Named("stateB") WfFormState stateB;
    private @Inject @Named("stateC") WfFormState stateC;
    private @Inject @Named("stateError") WfFormState stateError;

    public StateA() {
        super(new FormAConfig());
    }

    @Override
    public WfFormState handleEvent(Workflow workflow, WfEvent event) {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String name = event.getName();
        if ("next".equals(name)) {
            return stateAx;
        }

        if ("formAx".equals(name)) {
            return stateAx;
        }
        if ("formAy".equals(name)) {
            return stateAy;
        }
        if ("formB".equals(name)) {
            return stateB;
        }
        if ("formC".equals(name)) {
            return stateC;
        }
        if ("formError".equals(name)) {
            return stateAx;
        }
        return this;
    }
}
