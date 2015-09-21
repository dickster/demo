package forms;


import com.google.common.base.Preconditions;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.annotation.Nonnull;
import java.util.Map;

// do this in subclasses....-->@WfDef("commercial")
public abstract class Workflow<C extends IWorkflowContext> extends EventBus {

    private C context;

    protected WfState currentState;

    public Workflow(C context) {
        this.context = (C) context;
        register(this);
    }

    public Workflow<C> start() {
        Preconditions.checkState(currentState != null);
        Preconditions.checkState(context != null);
        initialize();
        return this;
    }

    @Subscribe
    public /*synchronized*/ void fire(@Nonnull WfEvent event) throws WorkflowException {
        try {
            WfState nextState = currentState.handleEvent(context, event);
            if (nextState!=null) {
                currentState = nextState;
                post(createChangeStateEvent(nextState, event));
            }
        } catch (Throwable t) {
            throw new WorkflowException("workflow failed when handling event", event, t);
        }
    }

    protected WfEvent<String> createChangeStateEvent(WfState state, WfEvent event) {
        // TODO : Create change event.
        return new WfEvent<String>("p");
    }

    @Subscribe
    protected void unhandledEvent(DeadEvent event) {
        System.out.println("an event occurred with no listeners " + event);
    }

    protected void initialize() {/*override if you want to do some pre-flight checks before workflow starts*/}

    public Object get(String key) {
        return context.get(key);
    }

    public <W extends Workflow> W withValue(String key, Object value) {
        context.put(key, value);
        return (W) this;
    }

    public Workflow<C> withValues(Map.Entry<String, Object>... values) {
        for (Map.Entry<String, Object> entry:values) {
            withValue(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public <W extends Workflow> W withValues(Map<String, Object> values) {
        for (String key:values.keySet()) {
            withValue(key, values.get(key));
        }
        return (W) this;
    }

    protected <T extends Workflow> T  withStartingState(WfState state) {
        this.currentState = state;
        return (T) this;
    }

    public <T extends Workflow> T restoreContext(C context) {
        this.context = context;
        return (T) this;
    }

    public C getContext() {
        return context;
    }


}
