package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;

public class RejectedState extends WfFormState {

    public RejectedState() {
        super(new RejectedFormConfig());
    }

    @Nonnull
    @Override
    public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("ok")) {
            workflow.end();
        }
        return unhandledEvent(workflow, event);
    }

}
