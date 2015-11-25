package forms.config;

import forms.WidgetTypeEnum;
import forms.WorkflowForm;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

public class FormConfig<T> extends GroupConfig<WorkflowForm> {

    private IFormValidator validator;
    private String url;

    public FormConfig(String name) {
        super(name, WidgetTypeEnum.FORM);
        withRenderBodyOnly(false);
        withCss("form");
    }

    @Override
    public WorkflowForm create(String id) {
        return new WorkflowForm(id, this);
    }

    public IFormValidator getValidator() {
        return validator;
    }

    public FormConfig<T> withValidator(IFormValidator validator) {
        this.validator = validator;
        return this;
    }

    public void setCallbackUrl(String url) {
        this.url = url;
    }
}
