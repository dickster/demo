package forms.widgets;

import com.google.common.eventbus.Subscribe;
import forms.WfValidationEvent;
import forms.config.Config;
import forms.config.FeedbackPanelConfig;
import forms.config.HasConfig;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class WfFeedbackPanel extends FeedbackPanel implements HasConfig {

    private FeedbackPanelConfig config;

    public WfFeedbackPanel(String id, FeedbackPanelConfig config) {
        super(id);
        this.config = config;
        setOutputMarkupPlaceholderTag(true);
    }

    @Override
    public boolean isVisible() {
        return anyMessage();
    }

    @Subscribe
    public void validationError(WfValidationEvent<?> event) {
        System.out.println("validation error occurred!");
        for (Object o:event.getResult().getErrors()) {
            // assert o!=null.
            event.getForm().error(o.toString());
        }
        event.getTarget().add(this);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
