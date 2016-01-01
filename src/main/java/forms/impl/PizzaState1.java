package forms.impl;

import forms.WfFormState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class PizzaState1 extends WfFormState {


    private @Inject PizzaState2 pizza2;

    public PizzaState1() {
        super(new Pizza1Config());
    }

    @Nonnull
    @Override
    public WfFormState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("Place Order")) {
            return pizza2;
        }
        return this;
    }
}

