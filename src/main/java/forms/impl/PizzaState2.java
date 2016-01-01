package forms.impl;

import forms.WfFormState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;

public class PizzaState2 extends WfFormState {

    public PizzaState2() {
        super(new Pizza2Config());
    }

    @Nonnull
    @Override
    public WfFormState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if ("Thanks, Come again".equals(event.getName())) {
            workflow.end();
        }
        return this;
    }
}
