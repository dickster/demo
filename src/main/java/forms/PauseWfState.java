package forms;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



public class PauseWfState extends WfState<FormBasedWorkflowContext> {

    private final String next;

    public PauseWfState(@Nonnull String nextState) {
        this.next = nextState;
    }

    @Nullable
    @Override
    public String handleEvent(FormBasedWorkflowContext context, WfEvent event) {
        // if resume...
        context.getProgress().hide();
        return next;
    }

    @Nullable
    @Override
    public String enter(FormBasedWorkflowContext context, WfEvent event) {
        // show progress bar in UI somehow?
        context.getProgress().show();

        // start other state in thread, when notified come back and jump to next state.
        new Runnable() {
            @Override public void run() {
                // do something here...
                getWorkflow().fire(new ResumeWfEvent());
            }
        };


    }



}
