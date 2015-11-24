package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;
import forms.config.FormAAnotherNewLayoutConfig;
import forms.config.FormAConfig;
import forms.config.FormANewLayoutConfig;
import forms.config.FormBConfig;
import forms.config.FormCConfig;
import forms.config.FormErrorConfig;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

public class StateA extends WfFormState {

    private @Inject @Named("stateAx") WfFormState stateAx;
    private @Inject @Named("stateB") WfFormState stateB;

    public StateA() {
        super(new FormAConfig());
    }

    @Override
    public WfFormState handleEvent(Workflow workflow, WfEvent event) {
        if ("next".equals(event.getName())) {
            return stateAx;
        }
        if ("formB".equals(event.getName())) {
            return stateB;
        }
        return this;
    }
}