package forms;

public class WfProgressEvent extends WfEvent {
    private int amount = 0;

    public WfProgressEvent(WfState state, WfEvent event, int amount) {
        super("progress");
        this.amount = amount;
    }

    public WfProgressEvent withAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
