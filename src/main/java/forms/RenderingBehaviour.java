package forms;

import com.google.common.base.Preconditions;
import forms.config.Config;
import forms.config.HasConfig;
import forms.util.ConfigGson;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class RenderingBehaviour extends Behavior {

    private static final String INIT_WIDGET_JS = "workflow.initWidget(%s);";

    // TODO : make this class a bean handled by container.  (and therefore allow injection of this gson).
    private ConfigGson gson = new ConfigGson();

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        Preconditions.checkState(component instanceof HasConfig, "only workflow components (created by widget factory) can use this behaviour");
        if ( component instanceof HasConfig) {
            Config config = ((HasConfig) component).getConfig();
            WidgetData data = new WidgetData(component.getMarkupId(), config);
            String js = String.format(INIT_WIDGET_JS, gson.toJson(data));
            response.render(OnDomReadyHeaderItem.forScript(js));
        }
    }

    class WidgetData {
        @Deprecated // should use the markup injected into config.
        String markupId;
        Config config;

        public WidgetData(String markupId, Config config) {
            this.markupId = markupId;
            this.config = config;
            config.withMarkupId(markupId);
        }
    }


}
