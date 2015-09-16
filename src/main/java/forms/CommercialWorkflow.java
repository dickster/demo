package forms;

import java.util.Locale;

public class CommercialWorkflow extends Workflow<FormBasedWorkflowContext> {

    private WfState startState = /*blah blah blah*/null;

    public CommercialWorkflow(FormBasedWorkflowContext context) {
        super(context);
        withStartingState(startState);
        withValue("defaultCountry", Locale.US);
    }

    protected void initialize() {
        ; //override if you want some workflow startup stuff to happen.
    }

}
