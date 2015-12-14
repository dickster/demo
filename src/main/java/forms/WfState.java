package forms;


import javax.annotation.Nonnull;
import java.io.Serializable;

public class WfState implements Serializable {

    private String name;

    public WfState() {
    }

    public WfState(String name) {
        this.name = name;
    }

    public String getName() {
        return name==null ? getClass().getSimpleName() : name;
    }


    public @Nonnull WfState handleError(Workflow workflow, WfSubmitEvent event) {
        return this;
    }

    public @Nonnull WfState handleEvent(Workflow workflow, WfSubmitEvent event) {
        // typically something like...
        // switch ( Event.valueOf(event.getId()) ) {
        //    case SUBMIT : doThis();
        //    case BIND : doThat();
        //    etc...
        // }
        return this; //event.getOnSuccessState();
    }

    // BA - no spring beans defined.  workflow just creates states based on form config which is named by onSuccessState().
    // return new WfState(formConfigWIthName(event.getSuccessState()));

    @Override
    public String toString() {
        return "WfState{" +
                "name='" + getName() + '\'' +
                '}';
    }

    // used w.r.t. browser history.  this is put in the url as you ajax-navigate through workflow.
    // if you want your urls to be prettier, then change this method.
    public String getStateName() {
        return getClass().getSimpleName();
    }

    protected WfState unhandledEvent(WfSubmitEvent event) {
        throw new IllegalArgumentException("the event " + event.getName() + " is not handled in state " + getClass().getSimpleName());
    }

}
