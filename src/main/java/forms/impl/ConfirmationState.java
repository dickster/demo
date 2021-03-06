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
    public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("finished")) {
            workflow.end();
        }
        return this;
    }
}
