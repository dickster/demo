package forms;

public class TestEventB extends WfState{

   // @Autowired
    private WfState transitionState;

    public TestEventB() {
        super();
    }

    public WfState getTransitionState() {
        return transitionState;
    };
}
