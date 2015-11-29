package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.Workflow;

import javax.inject.Inject;

public class PizzaState1 extends WfFormState {


    private @Inject PizzaState2 pizza2;

    public PizzaState1() {
        super(new Pizza1Config());
    }

    @Override
    public WfFormState handleEvent(Workflow workflow, WfEvent event) {
        if ("Place Order".equals(event.getName())) {
            return pizza2;
        }
        return this;
    }
}

