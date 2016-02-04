package forms;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

// TODO : i should just collapse Workflow & FormBasedWorkflow into one thing.
//   no use in trying to separate out wicket layer.
public class WfState implements Serializable {

    private String name;

    public WfState() {
        this.name = getClass().getSimpleName();
    }

    public WfState(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name==null ? getClass().getSimpleName() : name;
    }

    public @Nonnull WfState handleError(Workflow workflow, WfSubmitEvent event) {
        return this;
    }

    public @Nonnull WfState onEvent(Workflow workflow, WfSubmitEvent event) {
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

    protected WfState unhandledEvent(Workflow workflow, WfSubmitEvent event) {
        workflow.post(new WfUnhandledEvent(this, event));
        return this;
    }


    public void onEnter(Workflow workflow) {
        // override this if you want to do something when entering state.
        // for example, you might want to hide/show some fields depending on incoming model
        System.out.println("entering state " + getClass().getSimpleName());
    }
}
