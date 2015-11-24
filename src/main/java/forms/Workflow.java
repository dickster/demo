package forms;


import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import forms.model.WfCompoundPropertyModel;
import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;

public abstract class Workflow<T, S extends WfState> extends EventBus implements Serializable, BeanNameAware {

    private Map<String, Object> context = Maps.newHashMap();
    private WfCompoundPropertyModel<T> model;
    private WidgetFactory widgetFactory = new DefaultWidgetFactory();
    private S currentState;
    private boolean ended = false;
    private boolean started = false;

    private String beanName;

    public Workflow() {
        register(this);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public Workflow<T,S> initialize() {
        Preconditions.checkState(context != null);
        Preconditions.checkState(getCurrentState() != null);
        Preconditions.checkState(context != null);
        init(); // allow implementation specific initialization.
        started = true;
        return this;
    }

    protected void init() {
        // override if you want....extension point for impl stuff here.
    }

    @Subscribe
    public void handleAjaxEvent(@Nonnull WfAjaxEvent event) throws WorkflowException {
    }

    @Subscribe
    public final void fire(@Nonnull WfSubmitEvent event) throws WorkflowException {
        try {
            S nextState = (S) ((event instanceof WfSubmitErrorEvent) ?
                                getCurrentState().handleError(this, event) :
                                getCurrentState().handleEvent(this, event));
System.out.println("changing to state " + nextState);
            changeState(nextState, event);
        } catch (Throwable t) {
            throw new WorkflowException("workflow failed when handling event", event, t);
        }
    }

    protected void changeState(S nextState, WfSubmitEvent event) {
        validate(nextState);
        setCurrentState(nextState);
    }

    protected void validate(S nextState) {
        Preconditions.checkArgument(nextState != null, "can't have a null state for workflow.");
    }

    @Subscribe
    protected void unhandledEvent(DeadEvent event) {
        System.out.println("an event occurred with no listeners " + event);
    }

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

    public abstract S getStartingState();

    public <T extends Workflow> T restoreContext(Map<String, Object> context) {
        this.context = context;
        return (T) this;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    protected abstract @Nonnull WfCompoundPropertyModel<T> createModel();

    public WfCompoundPropertyModel<T> getModel() {
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

    public WidgetFactory getWidgetFactory() {
        return widgetFactory;
    }

    public String getBeanName() {
        return beanName;
    }

    public boolean isStarted() {
        return started;
    }

    public S getCurrentState() {
        if (currentState==null) {
            currentState=getStartingState();
        }
        return currentState;
    }

    public void setCurrentState(S currentState) {
        this.currentState = currentState;
    }
}
