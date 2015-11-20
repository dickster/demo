package forms;


import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import forms.model.WfCompoundPropertyModel;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;

// do this in subclasses....-->@WfDef("commercial")
public abstract class Workflow<T> extends EventBus implements Serializable {

    private Map<String, Object> context = Maps.newHashMap();
    private CompoundPropertyModel<T> model;
    private WidgetFactory widgetFactory = new DefaultWidgetFactory();
    protected WfState currentState;
    private boolean ended = false;

    public Workflow() {
        register(this);
    }

    public Workflow<?> start() {
        Preconditions.checkState(context != null);
        Preconditions.checkState(currentState != null);
        Preconditions.checkState(context != null);
        initialize();
        return this;
    }

    @Subscribe
    public final void fire(@Nonnull WfSubmitEvent event) throws WorkflowException {
        try {
            WfState nextState = (event instanceof WfSubmitErrorEvent) ?
                    currentState.handleError(this, event) :
                    currentState.handleEvent(this, event);
System.out.println("changing to state " + nextState);
Thread.sleep(1000);
            changeState(nextState, event);
        } catch (Throwable t) {
            throw new WorkflowException("workflow failed when handling event", event, t);
        }
    }

    protected void changeState(WfState nextState, WfSubmitEvent event) {
        validate(nextState);
        currentState = nextState;
    }

    protected void validate(WfState nextState) {
        Preconditions.checkArgument(nextState != null, "can't have a null state for workflow.");
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

    public Workflow withValues(Map.Entry<String, Object>... values) {
        for (Map.Entry<String, Object> entry:values) {
            withValue(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public <W extends Workflow> W withValues(Map<String, Object> values) {
        context.putAll(values);
        return (W) this;
    }

    public <T extends Workflow> T  withStartingState(WfState state) {
        this.currentState = state;
        return (T) this;
    }

    public <T extends Workflow> T restoreContext(Map<String, Object> context) {
        this.context = context;
        return (T) this;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    protected abstract @Nonnull WfCompoundPropertyModel<T> createModel();

    public CompoundPropertyModel<T> getModel() {
        if (model==null) {
            model = createModel();
        }
        return model;
    }

    public void end() {
        //currentState=null;
        ended = true;
    }

    public boolean isEnded() {
        return ended;
    }

    public WidgetFactory widgetFactory() {
        return widgetFactory;
    }

    public <T extends Workflow> T withWidgetFactory(WidgetFactory factory) {
        this.widgetFactory = factory;
        return (T) this;
    }

}
