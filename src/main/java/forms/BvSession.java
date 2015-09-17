package forms;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import javax.annotation.Nonnull;

public class BvSession extends WebSession {

    private Workflow<?> workflow;

    /**
     * Constructor. Note that {@link org.apache.wicket.request.cycle.RequestCycle} is not available until this constructor returns.
     *
     * @param request The current request
     */
    public BvSession(Request request) {
        super(request);
    }

    public Workflow<?> getWorkflow() {
        return workflow;
    }

    public void setWorkflow(@Nonnull Workflow<?> workflow) {
        this.workflow = workflow;
    }
}
