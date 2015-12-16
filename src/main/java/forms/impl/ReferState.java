package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;

public class ReferState extends WfFormState {

    public ReferState() {
        super(new ReferFormConfig());
    }

    @Nonnull
    @Override
    public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("ok")) {
            workflow.end();
        }
        return unhandledEvent(workflow, event);
    }
}
