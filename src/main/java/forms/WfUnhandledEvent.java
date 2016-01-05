package forms;

public class WfUnhandledEvent extends WfEvent<WfEvent> {

    private String state;

    public WfUnhandledEvent(WfState state, WfSubmitEvent event) {
        super(event, event.getTarget(), event.getComponent());
        String s = state.getName();
        String className = state.getClass().getSimpleName();
        this.state = className.equalsIgnoreCase(s) ?
                        s : className + "("+s+")";
    }

    public String getState() {
        return state;
    }
}
