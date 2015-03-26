package forms;


public class WorkflowVetoException extends WorkflowException {
    public WorkflowVetoException(String message) {
        super(message);
    }

    public WorkflowVetoException(WfEvent event, String message) {
        super(event, message);
    }

    public WorkflowVetoException(String message, Throwable cause) {
        super(message, cause);
    }
}
