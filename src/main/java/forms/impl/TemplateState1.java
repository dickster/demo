package forms.impl;

import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class TemplateState1 extends WfFormState {

    public TemplateState1() {
        super(new Template1FormConfig());
    }

    @Override
    @Nonnull public WfState onEvent(Workflow workflow, WfSubmitEvent event) {
        workflow.end();
        return this;
    }
}
