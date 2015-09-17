package forms;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WfFormState extends WfState<FormBasedWorkflowContext> {

    private FormConfig formConfig = new FormConfig();

    @Override
    public @Nullable WfState enter(FormBasedWorkflowContext context, WfEvent event) {
        // may need to lazily read formConfig here....from DB or home folder or whatever???
        return null;
    }

    @Override
    public @Nullable WfState handleEvent(FormBasedWorkflowContext workflow, WfEvent event) {
        return super.handleEvent(workflow, event);
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

}

