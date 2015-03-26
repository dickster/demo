package forms;


import javax.annotation.Nullable;
import java.io.Serializable;

public class WfState implements Serializable /*, BeanNameAware*/ {

    private String name;

    public WfState() {
    }

    void setBeanName(String name)  {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isTransitive() {
        // transitive states will always immediately jump to another state.  (as opposed to a state that would show a form and wait until an event happens, for example).
        return getTransitionEvent()!=null;
    }

    public WfEvent getTransitionEvent() {
        throw new UnsupportedOperationException("override this to implement your own");
    }

    public void leave(Workflow workflow, WfEvent event) throws WorkflowVetoException {
        // if state wants to VETO event, then queue another event here!
        // store in document cache here...
    }

    public @Nullable WfEvent enter(Workflow workflow, WfEvent event) {
        // load from document cache here...
        // do stuff...
        // binder.doBind(workflow.getCurrentData());
        return null;
    }

}
