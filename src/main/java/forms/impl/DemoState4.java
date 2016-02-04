package forms.impl;

import forms.*;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class DemoState4 extends WfFormState {

    private @Inject DemoState5 demoState5;

    public DemoState4() {
        super(new Demo4FormConfig());
    }

    @Override
    @Nonnull public WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if (event.is("next")) {
            return demoState5;
        }
        if (event.is("cancel")) {
            workflow.end();
            return this;
        }
        else {
            return unhandledEvent(workflow,event);
        }
    }
}
