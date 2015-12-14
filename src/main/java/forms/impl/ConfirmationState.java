package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;

public class ConfirmationState extends WfFormState {

    public ConfirmationState() {
        super(new ConfirmationFormConfig());
    }

    @Nonnull
    @Override
    public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("finish")) {
            workflow.end();
        }
        return this;
    }
}
