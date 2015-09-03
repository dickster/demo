package forms;

import java.util.List;

public class SubmitEvent extends WfEvent<List<String>> {

    public SubmitEvent(List<String> messages) {
        super(messages);
    }

    // NOT NEEDED!

    public boolean isSuccess() {
        return getObj().size()==0;
    }

    public boolean isError() {
        return !isSuccess();
    }

}
