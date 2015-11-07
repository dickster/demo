package forms;

import forms.config.FormConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WfFormState extends WfState {

    private FormConfig formConfig = new FormConfig();

    public WfFormState(FormConfig formConfig) {
        this.formConfig = formConfig;
    }

    @Nullable
    @Override
    public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
        return super.handleEvent(workflow, event);
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

}

