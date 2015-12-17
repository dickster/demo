package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.WfFeedbackPanel;

public class FeedbackPanelConfig extends Config<WfFeedbackPanel> {

    public FeedbackPanelConfig() {
        super("feedback", WidgetTypeEnum.FEEDBACK);
        withCss("alert alert-danger");
    }

    @Override
    public WfFeedbackPanel create(String id) {
        return new WfFeedbackPanel(id, this);
    }

}
