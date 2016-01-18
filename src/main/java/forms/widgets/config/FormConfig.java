package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.WorkflowForm;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

public class FormConfig<T> extends GroupConfig<WorkflowForm> {

    private IFormValidator validator;
    private FeedbackPanelConfig feedbackConfig;
    private String template;
    private @IncludeInJson String url; //historyCallbackUrl;

    public FormConfig(String name) {
        super(name, WidgetTypeEnum.FORM);
        withRenderBodyOnly(false);
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

    public FeedbackPanelConfig getFeedbackConfig() {
        return feedbackConfig;
    }

    public void setHistoryCallbackUrl(String url) {
        this.url = url;
    }

    @Override
    public Config withBehavior(String name) {
        throw new UnsupportedOperationException("you shouldn't be adding ajax behaviors to the form...add them to the contained components directly please and thank you.");
    }

    public FormConfig<T> withTemplate(String template) {
        this.template = template;
        return this;
    }

    public String getTemplate() {
        return template;
    }

}
