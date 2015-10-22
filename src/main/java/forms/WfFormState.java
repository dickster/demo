package forms;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WfFormState extends WfState {

    private FormConfig formConfig = new FormConfig();

    @Nullable
    @Override
    public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
        return super.handleEvent(workflow, event);
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

    private final void startHandlingEvent() {};

    private final void  endHandlingEvent() { }

}

