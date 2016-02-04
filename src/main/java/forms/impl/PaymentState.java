package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class PaymentState extends WfFormState {
    private @Inject @Named("confirmationState") WfState confirmationState;

    public PaymentState() {
        super(new PaymentFormConfig());
    }

    @Nonnull
    @Override
    public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return confirmationState;
        }
        return this;
    }
}
