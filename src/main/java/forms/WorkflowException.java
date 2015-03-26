package forms;


import javax.annotation.Nullable;

public class WorkflowException extends Exception {
    private WfEvent transitionEvent;

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(WfEvent event, String message) {
        super(message);
        this.transitionEvent = event;
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public @Nullable WfEvent getTransitionEvent() {
        return transitionEvent;
    }
}
