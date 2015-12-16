package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;
import forms.validation.IValidation;
import forms.validation.ValidationResult;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class InfoState extends WfFormState {

    private @Inject @Named("infoValidation") IValidation<String> infoValidation;
    private @Inject @Named("paymentState") WfState paymentState;
    private @Inject @Named("referState") WfState referState;

    public InfoState() {
        super(new InfoFormConfig());
    }

    @Nonnull
    @Override
    public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            ValidationResult<String> result = infoValidation.validate(workflow.getObject());
            if (!result.isSuccess()) {
                workflow.addValidationErrors(result);
                //workflow.post(new WfValidationEvent<String>(result, event.getTarget()));
                // Or... if you know what state you want to go to..?
                return referState;
                //return this;
            }
            else {
                return paymentState;
            }
        }
        return unhandledEvent(workflow, event);
    }
}
