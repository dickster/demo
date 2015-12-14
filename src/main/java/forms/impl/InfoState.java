package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;
import forms.impl.InfoFormConfig;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class InfoState extends WfFormState {

    private @Inject @Named("paymentState") WfState paymentState;

    public InfoState() {
        super(new InfoFormConfig());
    }

    @Nonnull
    @Override
    public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return paymentState;
        }
        return this;
    }
}
