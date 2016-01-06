package forms.impl;

import forms.*;
import forms.validation.IValidation;
import forms.validation.ValidationResult;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

public class InfoState extends WfFormState {

    private @Resource(name="infoValidation") IValidation<String> infoValidation;
    private @Resource(name="addressValidation") IValidation<String> addressValidation;

    private @Inject @Named("paymentState") WfState paymentState;
    private @Inject @Named("referState") WfState referState;

    public InfoState() {
        super(new InfoFormConfig());
    }

    @Nonnull
    @Override
    public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            ValidationResult<String> result = addressValidation.validate(workflow.getObject());
            if (!result.isSuccess()) {
                workflow.post(new WfValidationEvent<String>(result, event.getTarget(), event.getForm()));
                return this;
            }
//            result = infoValidation.validate(workflow.getObject());
//            if (!result.isSuccess()) {
//                workflow.addValidationErrors(result);
//                return referState;
//                //return this;
//            }
            return paymentState;
        }
        return unhandledEvent(workflow, event);
    }
}
