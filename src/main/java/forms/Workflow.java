package forms;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

// do this in subclasses....-->@WfDef("commercial")
public abstract class Workflow<C extends IWorkflowContext> extends EventBus {

    private C context;

    private WfState currentState;

    private @Inject WfStateFactory stateFactory;
    private Stack<Date> timer;

    private ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));


    public Workflow(C context) {
        this.context = (C) context;
        register(this);
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

    @Subscribe
    public void fire(@Nonnull String event) {
        // TODO : look up spring bean of class WfEvent & name = eventName.
        // otherwise create wrapper..
        fire(createEvent(event));
    }

    @Subscribe
    public void fire(@Nonnull WfEvent event) {
        String nextState = currentState.handleEvent(context, event);
        if (nextState!=null) {
            changeState(resolveState(nextState), event);
        }
    }

    @Subscribe
    protected void handleAjax(DeadEvent event) {
        System.out.println("an event occurred with no listeners " + event);
    }

//    @Subscribe
//    protected void handleAjax(WfAjaxEvent event) {
//        //override this if you want to listen to ajax events in your workflow!!!
//        System.out.println("AJAX EVENT " + event + " occurred but no listeners attached!");
//    }

    protected WfEvent createEvent(Object eventName) {
        // you might want to override this to add parameters, lookup spring beans with given name, ???
        return new WfEvent(eventName);
    }

    private @Nonnull WfState resolveState(String nextState) {
        return stateFactory.getState(nextState);
    }

    private final void changeState(WfState state, WfEvent event) {
        if (currentState.isAsync()) {
            changeAsyncState(state, event);
        }
        else {
            state.enter(context, event);
        }
        currentState = state;
    }

    private final void changeAsyncState(final WfState state, final WfEvent event) {
        enteringAsyncState(state);
        Callable<String> asyncTask = new Callable<String>() {
            @Override
            public @Nullable String call() throws Exception {
                return state.enter(context, event);
            }
        };
        Futures.addCallback(executor.submit(asyncTask), new FutureCallback<String>() {
            public void onSuccess(String result) {
                currentState = state;
                leavingAysncState(state);
            }

            public void onFailure(Throwable thrown) {
                ; // hmmm...what to do here??? leave in old state.
                System.out.println("couldn't enter state : " + state);
            }
        });
    }

    protected void enteringAsyncState(WfState state) {
        // override this to show progress bar, log, whatever...
        timer.push(new Date());
        System.out.println("starting state " + state);
    }

    private void leavingAysncState(WfState state) {
        // override this to hide progress bar, log, whatever...
        Date date = timer.pop();
        System.out.println("ending state " + state + " after " + (new Date().getTime()-date.getTime()) + " millis");
    }

    protected void initialize() {/*override if you want to do some pre-flight checks before workflow starts*/}

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
