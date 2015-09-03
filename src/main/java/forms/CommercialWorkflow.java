package forms;

import java.util.Locale;

public class CommercialWorkflow extends Workflow<String> {

    private WfState startState = /*blah blah blah*/null;

    public CommercialWorkflow() {
        super();
            withStartingState(startState)
            .withValue("defaultCountry", Locale.US)
            .withData(new String("hello world"));
    }

    protected void initialize() {
        ; //override if you want some workflow startup stuff to happen.
    }

}
