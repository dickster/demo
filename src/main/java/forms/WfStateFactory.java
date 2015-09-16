package forms;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nonnull;

// TODO : make this "SpringStateFactory" which implements this interface.
// a default/test one will simply create form states based on the name.  but that will only be used in testing app by BA's.
public class WfStateFactory /* implements ApplicationContextAware */ {

    private ApplicationContext context;

    // note : state names should have a workflow prefix.
    //   eg. commerical.cmf   commerical.ai   etc...
    //       email.state1,    policyChange.summary...
    public @Nonnull WfState getState(String stateName) {

        // IMPORTANT : these spring beans should be defined as prototype, not springletons.
//        Preconditions.checkArgument(StringUtils.isNotBlank(stateName), "you must specify valid non-blank state name.");
//        Map<String,WfState> states = context.getBeansOfType(WfState.class);
//        for (WfState state:states.values()) {
//            if (state.getName().equals(stateName)) {
//                Preconditions.checkArgument(context.isPrototype(state.getName(), " state beans must be prototype scoped. change the spring configuration accordingly."));
//                return state;
//            }
//        }
        // for debugging only.  should never reach here for production.
        // note that this state, will ironically not be stateful because i just create one as needed.
        // this should be good enough for debugging environment.
        return new WfState();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}