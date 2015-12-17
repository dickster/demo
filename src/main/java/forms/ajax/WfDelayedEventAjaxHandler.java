package forms.ajax;

import com.google.gson.Gson;
import forms.WfAjaxHandler;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class WfDelayedEventAjaxHandler extends WfAjaxHandler {

    public static final String DELAYED_EVENT = "delayed_event";
    private static final String INIT_JS = "$('#%s').delayedEvent(%s);";
    private final String event;

    public WfDelayedEventAjaxHandler(String event) {
        super(DELAYED_EVENT);
        this.event = event;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        String markupId = component.getMarkupId();
        String options = new Gson().toJson(new DelayedChangeOptions(markupId));
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_JS, markupId, options)));
    }

//    private DelayedChangeOptions getOptions() {
//        return null;
//    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        System.out.println("wicket delayed event called...");
    }

    class DelayedChangeOptions {
        String event = WfDelayedEventAjaxHandler.this.event;
        String delayedEvent = DELAYED_EVENT;
        String markupId;
        private Integer threshold = 300;

        public DelayedChangeOptions(String markupId) {
            this.markupId = markupId;
        }
    }

}
