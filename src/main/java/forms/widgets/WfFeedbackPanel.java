package forms.widgets;

import com.google.common.eventbus.Subscribe;
import forms.WfSubmitErrorEvent;
import forms.WfValidationEvent;
import forms.widgets.config.Config;
import forms.widgets.config.FeedbackPanelConfig;
import forms.widgets.config.HasConfig;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class WfFeedbackPanel extends FeedbackPanel implements HasConfig {

    private FeedbackPanelConfig config;

    public WfFeedbackPanel(String id, FeedbackPanelConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    public boolean isVisible() {
        return anyMessage();
    }

    @Subscribe
    public void onSubmitError(WfSubmitErrorEvent event) {
        event.getTarget().add(this);
    }

    @Subscribe
    public void onValidationError(WfValidationEvent<?> event) {
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
