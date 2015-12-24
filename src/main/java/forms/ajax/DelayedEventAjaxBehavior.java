package forms.ajax;

import com.google.gson.Gson;
import forms.spring.WfAjaxBehavior;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class DelayedEventAjaxBehavior extends WfAjaxBehavior {

    public static final String DELAYED_EVENT = "delayed_event";
    private static final String INIT_JS = "$('#%s').delayedEvent(%s);";
    private final String event;

    public DelayedEventAjaxBehavior(String event) {
        super(DELAYED_EVENT);
        this.event = event;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        String markupId = component.getMarkupId();
        String options = new Gson().toJson(new DelayedChangeOptions(markupId));
        //assumes delayedEvent plugin is already installed.  prolly not great assumption.
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_JS, markupId, options)));
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {

    }

    class DelayedChangeOptions {
        String event = DelayedEventAjaxBehavior.this.event;
        String delayedEvent = DELAYED_EVENT;
        String markupId;
        private Integer threshold = 300;

        public DelayedChangeOptions(String markupId) {
            this.markupId = markupId;
        }
    }

}
