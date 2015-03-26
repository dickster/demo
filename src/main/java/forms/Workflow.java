package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

// do this in subclasses....-->@WfDef("commercial")
public abstract class Workflow<T extends Serializable> {

    private Map<String, Object> context = Maps.newHashMap();

    private WfState currentState;

    private transient WfEvent nextEvent;

    // really, this should be in the context with convenience methods to access?
    private T data; ///document? IModel?

    public Workflow() {
    }

    public Workflow start(WfEvent event) {
        Preconditions.checkState(currentState == null);
        Preconditions.checkState(getModelObject() != null);
        intialize(context);
        fire(event);
        return this;
    }

    public Workflow start(final WfState state) {
        start(new WfEvent() {
            @Override @Nonnull
            public WfState getTransitionState() {
                return state;
            }
        });
        return this;
    }

    public void fire(@Nonnull WfEvent event) {
        nextEvent = event;
        doWorkflow();
    }

    private void doWorkflow() {
        while (nextEvent != null) {
            try {
                doWorkflowEvent(nextEvent);
                // if the state is temporary (i.e. it always jumps to another state after entering, then handle this otherwise just stay here.
                if (currentState.isTransitive()) {
                    nextEvent = currentState.getTransitionEvent();
                } else {
                    nextEvent = null;
                }
            } catch (WorkflowException e) {
                // tried to switch to another state but failed...will remain in this state unless the exception gives us directions on where to go.
                nextEvent = e.getTransitionEvent();  // <--nullable
            }

        }
    }

    private void doWorkflowEvent(@Nonnull WfEvent event) throws WorkflowVetoException {
        WfState transitionState = event.getTransitionState();
        if (currentState!=null) {
            currentState.leave(this, event);
        }
        // how to handle exceptions here???
        transitionState.enter(this, event);
        currentState = transitionState;
    }

    protected T getModelObject() {
        return data;
    }

    protected void intialize(Map<String, Object> context) {
        ; //override if you want some workflow startup stuff to happen.
    }

    public void fire(@Nonnull String eventName) {
        WfEvent event = getEventByName(eventName);
        fire(event);
    }

//    public void queue(@Nonnull WfState nextState) {
//        queue(new WfEventAdapter(nextState));
//    }

    private WfEvent getEventByName(String eventName) {
        return null; // TODO
    }

    public WfState getCurrentState() {
        return currentState;
    }

    public Object get(String key) {
        return context.get(key);
    }

    public Workflow withValue(String key, Object value) {
        context.put(key, value);
        return this;
    }

    public Workflow withValues(Entry<String, Object>... values) {
        for (Entry<String, Object> value:values) {

            context.put(value.getKey(), value.getValue());
        }
        return this;
    }

    public Workflow withValues(Map<String, Object> values) {
        context.putAll(values);
        return this;
    }

    public Workflow withData(T data) {
        this.data = data;
        return this;
    }

}
