package forms;

import forms.config.FormConfig;

import javax.annotation.Nonnull;

public class WfFormState extends WfState {

    private FormConfig formConfig;

    public WfFormState(@Nonnull FormConfig formConfig) {
        this.formConfig = formConfig;
    }

    @Override
    public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
        return super.handleEvent(workflow, event);
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

}

