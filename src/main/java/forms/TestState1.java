package forms;


public class TestState1 extends WfState {

   // @Autowired
    private WfEvent event1;

    //@Autowired
    private WfEvent event2;


    public TestState1() {

    }

    public void leave(Workflow workflow, WfEvent event) throws WorkflowVetoException {
        ; // do nothing.
    }

    public WfEvent enter(Workflow workflow, WfEvent event) {
        // load from document cache here...
        // do stuff...
        // binder.doBind(workflow.getCurrentData());

        if (isSomeConditionToFireAnEvent()) {
            return event1;
        } else {
            return event2;
        }

    }

    protected boolean isSomeConditionToFireAnEvent() {
        return true;
    }


}
