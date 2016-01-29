package forms;


import javax.annotation.Nullable;

public class WorkflowException extends RuntimeException {
    private WfEvent transitionEvent;

    public WorkflowException(WfEvent event, String message) {
        super(message);
        this.transitionEvent = event;
    }

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(Throwable t) {
        this(t.getMessage(), t);
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
