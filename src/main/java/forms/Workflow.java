package forms;


import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import forms.model.WfCompoundPropertyModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Map;

public abstract class Workflow<T, S extends WfState> extends EventBus implements Serializable, BeanNameAware, ApplicationContextAware {

    private WidgetFactory widgetFactory;
    private transient ApplicationContext applicationContext;
    private Map<String, Object> context = Maps.newHashMap();
    private WfCompoundPropertyModel<T> model;
    private S currentState;
    private boolean ended = false;
    private boolean started = false;
    private String beanName;
    protected transient Map<String, S> statesVisited = Maps.newHashMap();


    public Workflow() {
        register(this);
        this.widgetFactory = getWidgetFactory();
    }

    @PostConstruct
    public void ensurePrototypeBean() {
        Preconditions.checkState(applicationContext.isPrototype(beanName), "workflow beans must be of @Scope('prototype') " + getClass().getSimpleName());
        // note : don't use application context after this.  it's just a transient var used at construction time.
        // it is NOT meant to be serialized by Wicket.
        applicationContext = null; //invalidate this just to make the point clear.
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public Workflow<T,S> initialize() {
        Preconditions.checkState(context != null);
        init(); // allow implementation specific initialization.
        // do prevals here...
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
    public void debug(@Nonnull WfDebugEvent event) {
        ; //  override this if you want to handle debug events.
        // should assert that you only get these in debug mode!!!
    }

    @Subscribe
    public final void fire(@Nonnull WfSubmitEvent event) throws WorkflowException {
        try {
            S nextState = (S) ((event instanceof WfSubmitErrorEvent) ?
                                getCurrentState().handleError(this, event) :
                                getCurrentState().handleEvent(this, event));
            changeState(nextState, event);
        } catch (Throwable t) {
            throw new WorkflowException("workflow failed when handling event", event, t);
        }
    }

    protected void changeState(S nextState, WfSubmitEvent event) {
        changeState(nextState);
    }

    protected final void changeState(S nextState) {
        validate(nextState);
        setCurrentState(nextState);
        statesVisited.put(getCurrentStateName(), getCurrentState());
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

    public Workflow withModel(WfCompoundPropertyModel<T> model) {
        this.model = model;
        return this;
    }

    public void end() {
        //currentState=null;
        ended = true;
    }

    public boolean isEnded() {
        return ended;
    }

    public abstract WidgetFactory getWidgetFactory();

    public Workflow withWidgetFactory(WidgetFactory widgetFactory) {
        // NOTE : only use this if you really want to override default factory.
        //  typically, using the default is the smart thing to do.
        this.widgetFactory = widgetFactory;
        return this;
    }

    public String getBeanName() {
        return beanName;
    }

    public boolean isStarted() {
        return started;
    }

    protected final S getCurrentState() {
        if (currentState==null) {
            setCurrentState(getStartingState());
        }
        return currentState;
    }

    public void setCurrentState(S state) {
        this.currentState = state;
        statesVisited.put(getCurrentStateName(), currentState);
    }

    public String getCurrentStateName() {
        return currentState.getStateName();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
