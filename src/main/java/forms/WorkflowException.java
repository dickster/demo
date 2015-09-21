package forms;


import javax.annotation.Nullable;

public class WorkflowException extends Exception {
    private WfEvent transitionEvent;

    public WorkflowException(WfEvent event, String message) {
        super(message);
        this.transitionEvent = event;
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkflowException(String s, WfEvent event, Throwable cause) {
        super(s, cause);
        this.transitionEvent = event;
    }

    public @Nullable WfEvent getTransitionEvent() {
        return transitionEvent;
    }
}
