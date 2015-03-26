package forms;

import java.util.Locale;
import java.util.Map;

@WfDef(name="CommlPkgPolicyRq")  // or use actual class @WfDef(type=CommlPkgPolicyRq.class)
public class CommercialWorkflow extends Workflow<String> {

//    @Autowired a groovy bean.  need to make an interface for WfState & WfEvent.
    private WfState testState;

    public CommercialWorkflow() {
        super();
            withValue("defaultCountry", Locale.US).
            withData("hello world");
    }

    public CommercialWorkflow start() {
        start(testState);
        return this;
    }

    protected void intialize(Map<String, Object> context) {
        ; //override if you want some workflow startup stuff to happen.
    }

}
