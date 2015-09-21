package forms;

public class WfChangeStateEvent extends WfEvent {

    private WfState state;
    private WfEvent triggerEvent;

    public WfChangeStateEvent(WfState current, WfEvent trigger) {
        super("CHANGE_PAGE");
        this.state = current;
        this.triggerEvent = trigger;
    }

    public WfState getState() {
        return state;
    }

    public WfEvent getTriggerEvent() {
        return triggerEvent;
    }


}
