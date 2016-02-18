package forms.widgets.config;

import com.google.gson.annotations.Expose;
import forms.WidgetTypeEnum;
import forms.WorkflowForm;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

public class FormConfig<T> extends GroupConfig<WorkflowForm> {

    private FeedbackPanelConfig feedbackConfig;
    private @Expose String url; //historyCallbackUrl;

    public FormConfig(String name) {
        super(name, WidgetTypeEnum.FORM);
    }

    @Override
    public WorkflowForm create(String id) {
        throw new UnsupportedOperationException("you should never create the top level workflow component through the widget factory.");
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

}
