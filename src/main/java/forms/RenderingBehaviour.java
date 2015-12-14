package forms;

import com.google.common.base.Preconditions;
import forms.config.Config;
import forms.config.HasConfig;
import forms.util.ConfigGson;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxRegionMarkupIdProvider;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;

public class RenderingBehaviour extends Behavior implements IAjaxRegionMarkupIdProvider {

    private static final String INIT_WIDGET_JS = "workflow.initWidget(%s);";

    // TODO : make this class a bean handled by container.  (and therefore allow injection of this gson).
    private ConfigGson gson = new ConfigGson();
    private boolean wrapComponent;
    private HasConfig comp;


    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        // fix the tags here...!yay!!
        // don't need to write custom components here.!
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        Config config = comp.getConfig();
        // inject the markup id (need to do this everytime 'cause it's always changing).
        config.withMarkupId(component.getMarkupId());
        String js = String.format(INIT_WIDGET_JS, gson.toJson(config));
        response.render(OnDomReadyHeaderItem.forScript(js));
    }

    @Override
    public void bind(Component component) {
        Preconditions.checkState(component instanceof HasConfig, "only workflow components (created by widget factory) can use this behaviour");
        if ( component instanceof HasConfig) {
            comp = (HasConfig) component;
        }
        this.wrapComponent = !(component instanceof Panel)
                || component instanceof TextField
                || component instanceof DropDownChoice
                || component instanceof CheckBox
                || component instanceof TextArea
                || component instanceof Radio
                || component instanceof RadioGroup;
    }

    public void beforeRender(Component c) {
        if (wrapComponent) {
            c.getResponse().write("<div id='" + getWfId(c) + "'>");
        }
    }

    public void afterRender(Component c) {
        if (wrapComponent) {
            c.getResponse().write("</div>");
        }
    }

    @Override
    public String getAjaxRegionMarkupId(Component component) {
        return wrapComponent ? getWfId(component) : component.getMarkupId();
    }

    private String getWfId(Component component) {
        return component.getMarkupId()+"_wf";
    }
}
