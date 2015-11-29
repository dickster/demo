package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.Workflow;

public class PizzaState2 extends WfFormState {

    public PizzaState2() {
        super(new Pizza2Config());
    }

    @Override
    public WfFormState handleEvent(Workflow workflow, WfEvent event) {
        if ("Thanks, Come again".equals(event.getName())) {
            workflow.end();
        }
        return this;
    }
}
