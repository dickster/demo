package forms;


import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import forms.model.WfCompoundPropertyModel;
import forms.validation.ValidationResult;
import forms.widgets.config.Config;
import org.apache.wicket.Component;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Map;


/** localization.   // underlying prob = need a central place to change form.
 * changing text en mass.  e.g. change "submit" to "next".
 * styling and layout not controlled.
 * BA can't preview the complete form.  testing more difficult/less isolated.
 * form & workflow should be more tightly coupled with underlying model.
 *  (not in different projects).
 */

public abstract class Workflow<T, S extends WfState> extends EventBus implements Serializable, BeanNameAware {

    private @Inject BeanFactory beanFactory;

    // used for history (back/fwd buttons)
    protected transient Map<String, S> statesVisited = Maps.newHashMap();

    private Map<String, Object> context = Maps.newHashMap();
    private WfCompoundPropertyModel<T> model;
    private S currentState;
    private boolean ended = false;
    private boolean started = false;
    private String beanName;

    public Workflow() {
        register(this);
    }

    @PostConstruct
    public void ensurePrototypeBean() {
        Preconditions.checkState(beanFactory.isPrototype(beanName), "workflow beans must be of @Scope('prototype') " + getClass().getSimpleName());
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public final Workflow<T,S> initialize() {
        Preconditions.checkState(context != null);
        init(); // allow implementation specific initialization.
        started = true;
        setState(getStartingState());
        return this;
    }

    protected void init() {
        // override if you want....extension point for impl stuff here.
        // do prevals here...
    }

    @Subscribe void onSubmitError(WfSubmitErrorEvent event) throws WorkflowException {
        System.out.println("submit error : " + event.getName());
    }

    @Subscribe
    public void onDebug(@Nonnull WfDebugEvent event) {
        ; //  override this if you want to handle debug events.
        // should assert that you only get these in debug mode!!!
    }

    @Subscribe
    public void onUnhandled(WfUnhandledEvent event) {
        String js = String.format(
                "alert('the state [%s] did not handle the submit event [%s]');",
                event.getState(),
                event.getObj().getName());
        event.getTarget().appendJavaScript(js);

    }

    @Subscribe
    public final void onSubmit(@Nonnull WfSubmitEvent event) throws WorkflowException {
        try {
            reset();
            S nextState = (S) getCurrentState().onEvent(this, event);
            changeState(nextState, event);
        } catch (Throwable t) {
            throw new WorkflowException("workflow failed when handling event", event, t);
        }
    }

    protected void reset() {
        // your chance to clear any session stuff like errors, msgs, state values.
    }

    public void addValidationErrors(ValidationResult<?> result) {
        Preconditions.checkArgument(!result.isSuccess(), "you are adding errors for a validation that passed?");
        for (Object error:result.getErrors()) {
            addError(error);
        }
    }

    public void addError(Object error) {
        throw new UnsupportedOperationException("you need to implement this method in order to support error handling (and clearErrors() too!)");
    }

    @Subscribe
    public void onValidation(WfValidationEvent event) throws WorkflowException {
        // override to handle validation errors.
    }

    protected final void changeState(S nextState, WfSubmitEvent event) {
        setState(nextState);
        onChangeState(nextState, event);
    }

    protected abstract void onChangeState(S nextState, WfSubmitEvent event);

    protected final boolean setState(S nextState) {
        if (nextState.equals(getCurrentState())) {
            return false;
        }
        validate(nextState);
        setCurrentState(nextState);
        statesVisited.put(getCurrentStateName(), getCurrentState());
        nextState.onEnter(this);
        return true;
    }

    protected void validate(S nextState) {
        Preconditions.checkArgument(nextState != null, "can't have a null state for workflow.");
    }

    @Subscribe
    public void deadEvent(DeadEvent event) {
        System.out.println("an event occurred with no listeners " + event);
        // TODO : somehow send this to current page...override in FormBasedWorkflow.
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

    public T getObject() {
        return getModel().getObject();
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

    // hmm...i should still use a factory to create this???
    public abstract WidgetFactory getWidgetFactory();

    public String getBeanName() {
        return beanName;
    }

    public boolean isStarted() {
        return started;
    }

    protected final S getCurrentState() {
        return currentState;
    }

    public void setCurrentState(S state) {
        this.currentState = state;
        statesVisited.put(getCurrentStateName(), currentState);
    }

    public String getCurrentStateName() {
        return currentState.getStateName();
    }

    public Component createWidget(String id, Config config) {
        Component widget = getWidgetFactory().createWidget(id, config);
        register(widget);
        return widget;
    }
}
