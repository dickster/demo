package forms;

import org.apache.wicket.Component;

public class WfChangePageEvent extends WfEvent {

    // TODO : pass this in.
    private Component source;

    public WfChangePageEvent() {
        super("CHANGE_PAGE");
    }

    public Component getSource() {
        return source;
    }
}
