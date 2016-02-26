package forms;


import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import forms.model.WfCompoundPropertyModel;
import forms.validation.ValidationResult;
import forms.widgets.config.Config;
import forms.widgets.config.FormConfig;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;
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

public abstract class Workflow<T> extends EventBus implements Serializable, BeanNameAware {

    private @Inject BeanFactory beanFactory;

    // used for history (back/fwd buttons)
    protected transient Map<String, WfFormState> statesVisited = Maps.newHashMap();

    private Map<String, Object> context = Maps.newHashMap();
    private WfCompoundPropertyModel<T> model;
    private WfFormState currentState;
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

    public final Workflow<T> initialize() {
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
            WfFormState nextState =  (WfFormState)getCurrentState().onEvent(this, event);
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

    protected final void changeState(WfFormState nextState, WfSubmitEvent event) {
        setState(nextState);
        onChangeState(nextState, event);
    }

    protected final boolean setState(WfFormState nextState) {
        if (nextState.equals(getCurrentState())) {
            return false;
        }
        validate(nextState);
        setCurrentState(nextState);
        statesVisited.put(getCurrentStateName(), getCurrentState());
        nextState.onEnter(this);
        return true;
    }

    protected void validate(WfFormState nextState) {
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

    public abstract WfFormState getStartingState();

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

    protected final WfFormState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(WfFormState state) {
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

    public FormConfig getCurrentFormConfig() {
        return getCurrentState().getFormConfig();
    }

    @Subscribe
    public void onDebug(@Nonnull WfDebugEvent event) {
        refreshForm(event);
    }

    private void refreshForm(WfEvent event) {
        WorkflowForm form = getWorkflowForm(event.getComponent());
        updateFormViaAjax(form, event.getTarget());
    }

    public WorkflowForm getWorkflowForm(Component component) {
        return component.findParent(WorkflowForm.class);
    }

    @Subscribe
    public void onValidation(WfValidationEvent event) {
    }

    @Subscribe
    public void onSubmitError(WfSubmitErrorEvent event) throws WorkflowException {
    }

    protected void onChangeState(WfFormState nextState, WfSubmitEvent event) {
        updateFormViaAjax(event);
    }

    protected void updateFormViaAjax(WfSubmitEvent event) {
        WorkflowForm form = event.getForm().findParent(WorkflowForm.class);
        updateFormViaAjax(form, event.getTarget());
        event.getTarget().appendJavaScript("wf.pushHistory();");
    }

    protected void updateFormViaAjax(WorkflowForm form, AjaxRequestTarget target) {
        WorkflowForm newForm = createForm(form.getId(), getCurrentState());
        form.replaceWith(newForm);
        target.add(newForm);
    }

    protected void updatePage(WebPage page) {
        RequestCycle requestCycle = RequestCycle.get();
        if (requestCycle!=null) {
            requestCycle.setResponsePage(page);
        } else {
            System.out.println("your workflow is redirecting to a page without a request cycle?  huh???");
        }
    }

    public WorkflowForm createForm(String id, WfFormState state) {
        return new WorkflowForm(id, state);
    }

    public void gotoState(String state, AjaxRequestTarget target, WorkflowForm form) {
        // note that we don't allow user to go to states that he hasn't been in.
        // that means if you paste in a history generated url after session expires you will probably bomb here....
        // not sure how to handle it gracefully...just go to starting state? or any visitedState?
        Preconditions.checkState(statesVisited.get(state) != null, " can't go to state " + state + " because you haven't been to it yet? {" + statesVisited.keySet() + "}");
        setState(statesVisited.get(state));
        updateFormViaAjax(form, target);
    }




}
