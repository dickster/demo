package forms;


import javax.annotation.Nullable;
import java.io.Serializable;

public class WfState<C extends IWorkflowContext> implements Serializable {

    private String name;

    public WfState() {
    }

    public WfState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public @Nullable <T extends WfState> T handleEvent(C workflow, WfEvent event) {
        // typically something like...
        // switch ( Event.valueOf(event.getName()) ) {
        //    case SUBMIT : doThis();
        //    case BIND : doThat();
        //    etc...
        // }
        // return  if null, workflow will just hang around and wait for next event.
        return null; //event.getOnSuccessState();
    }


    // BA - no spring beans defined.  workflow just creates states based on form config which is named by onSuccessState().
    // return new WfState(formConfigWIthName(event.getSuccessState()));


    @Override
    public String toString() {
        return "WfState{" +
                "name='" + name + '\'' +
                '}';
    }
}
