package forms;

import forms.config.FormConfig;

import javax.annotation.Nonnull;

public class WfFormState extends WfState {

    private FormConfig formConfig;

    public WfFormState(@Nonnull FormConfig formConfig) {
        this.formConfig = formConfig;
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

}

