package forms;


public class WfChangeFormStateEvent extends WfChangeStateEvent {

    public WfChangeFormStateEvent(WfState state, WfEvent event) {
        super(state, event);
    }
}
