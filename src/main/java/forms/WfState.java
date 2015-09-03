package forms;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

public class WfState implements Serializable /*, BeanNameAware*/ {

    private String name;

    public WfState() {
    }

    private WfState(String name) {
        this.name = name;
    }

    // typically, should be created as spring beans.  but early in development cycle you can get away with these
    //  skeleton classes.
    public static WfState createUnmanagedState(@Nonnull String name) {
        // TODO : make this state find a form of the same name.
        return new WfState(name);
    }

    void setBeanName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public @Nullable String handleEvent(IWorkflowContext workflow, WfEvent event) {
        // typically something like...
        // switch ( Event.valueOf(event.getName()) ) {
        //    case SUBMIT : doThis();
        //    case BIND : doThat();
        //    etc...
        // }
        // return  if null, workflow will just hang around and wait for next event.
        return event.getOnSuccessState();
    }

    public @Nullable String enter(IWorkflowContext workflow, WfEvent event) {
        // load from document cache here...
        // do stuff...
        // get parameters & data from event if needed.
        // binder.doBind(workflow.getCurrentData());
        return null;
    }

}
