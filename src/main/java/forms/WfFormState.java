package forms;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WfFormState extends WfState<FormBasedWorkflowContext> {

    private FormConfig formConfig = new FormConfig();

    @Override
    public @Nullable WfState handleEvent(FormBasedWorkflowContext workflow, WfEvent event) {
        return super.handleEvent(workflow, event);
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

    private final void startHandlingEvent() {};

    private final void  endHandlingEvent() { }

}

