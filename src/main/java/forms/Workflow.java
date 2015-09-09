package forms;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Map.Entry;

// do this in subclasses....-->@WfDef("commercial")
public abstract class Workflow<C extends IWorkflowContext> {

    private C context;

    private WfState currentState;

    public Workflow(WfState state, C context) {
        withStartingState(state);
        this.context = context;
    }

    public Workflow start() {
        Preconditions.checkState(currentState != null);
        Preconditions.checkState(context != null);
        initialize();
        currentState.enter(context, new WfEvent("STARTING"));
        return this;
    }

    public Workflow start(@Nonnull final WfState state) {
        Preconditions.checkState(currentState == null);
        withStartingState(state);
        start();
        return this;
    }

    public void fire(Object event) {
        // TODO : look up spring bean of class WfEvent & name = eventName.
        // otherwise create wrapper..
        fire(createEvent(event));
    }

    protected WfEvent createEvent(Object eventName) {
        // you might want to override this to add parameters, lookup spring beans with given name, ???
        return new WfEvent(eventName);
    }

    public void fire(@Nonnull WfEvent event) {
        String nextState = currentState.handleEvent(context, event);
        if (nextState!=null) {
            changeState(resolveEvent(nextState));
        }
    }

    protected void changeState(WfState state) {
        currentState = state;
    }

    protected WfState resolveEvent(@Nonnull String e) {
        return WfState.createUnmanagedState(e);
    }

    protected void initialize() {}

    public Object get(String key) {
        return context.get(key);
    }

    public <W extends Workflow> W withValue(String key, Object value) {
        context.put(key, value);
        return (W) this;
    }

    public Workflow withValues(Entry<String, Object>... values) {
        for (Entry<String, Object> entry:values) {
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
