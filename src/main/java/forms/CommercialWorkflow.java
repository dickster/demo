package forms;

import javax.annotation.Resource;
import java.util.Locale;

public class CommercialWorkflow extends FormBasedWorkflow {

    private @Resource(name="commercialStartState")
    WfState startState;

    public CommercialWorkflow(FormBasedWorkflowContext context) {
        super(context);
        withStartingState(startState);
        withValue("defaultCountry", Locale.US);
    }

    protected void initialize() {
        ; //override if you want some workflow startup stuff to happen.
    }

}
