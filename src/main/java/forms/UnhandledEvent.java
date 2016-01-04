package forms;

public class UnhandledEvent extends WfEvent<WfEvent> {

    public UnhandledEvent(WfEvent event) {
        super(event, event.getTarget(), event.getComponent());
    }

}
