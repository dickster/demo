package forms;

import com.google.common.base.Preconditions;
import forms.util.ConfigGson;
import forms.util.WfUtil;
import forms.widgets.config.Config;
import forms.widgets.config.GroupConfig;
import forms.widgets.config.HasConfig;
import forms.widgets.config.HasTemplate;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxRegionMarkupIdProvider;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import javax.inject.Inject;
import java.util.Map;

public class RenderingBehavior extends Behavior implements IAjaxRegionMarkupIdProvider {

    // TODO : should shorten the .js name to reduce size of payload.    wf.widget(%s) ??
    private static final String INIT_WIDGET_JS = "wf.widget(%s);";

    private @Inject ConfigGson gson;

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
        addAttributes(component, tag);
    }

    private void addAttributes(Component component, ComponentTag tag) {
        Config config = getConfig(component);
        Map<String, String> attributes = config.getAttributes();
        for (String key:attributes.keySet()) {
            tag.getAttributes().put(key, attributes.get(key));
        }
        if (!needsToBeWrapped(component)) {
            // this should EITHER be put on component markup OR it's wrapped parent's markup. (not both).
            tag.getAttributes().put("data-wf", getDataWf(component));
        }
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        Config config = getConfig(component);

        // this is our chance to inject/alter a bunch of stuff that we send to the browser.
        // inject the markup id (need to do this everytime 'cause it's always changing).
        // the .js code will typically need this to find the element.
        config.injectMarkupId(component.getMarkupId());

        injectTemplateId(component, config, response);

        String js = String.format(INIT_WIDGET_JS, gson.toJson(config));
        response.render(OnDomReadyHeaderItem.forScript(js));
    }

    private void injectTemplateId(Component component, Config config, IHeaderResponse response) {
        if (config instanceof GroupConfig) { // i.e. config creates 'HasTemplate' components.
            // assert component instanceof HasTemplate...
            String id = ((HasTemplate)component).getTemplateId();
            GroupConfig groupConfig = (GroupConfig) config;
            groupConfig.injectTemplateId(id);
        }
    }

    private Config getConfig(Component component) {
        if (component instanceof HasConfig) {
            return ((HasConfig)component).getConfig();
        }
        throw new IllegalArgumentException("component does not implement " + HasConfig.class.getSimpleName());
    }

    @Override
    public void bind(Component component) {
        Preconditions.checkState(component instanceof HasConfig, "only workflow components (created by widget factory) can use this behaviour");
    }

    public void beforeRender(Component c) {
        if (needsToBeWrapped(c)) {
            c.getResponse().write("<div id='" + getWrappedId(c) + "' data-wf='" + getDataWf(c) + "'>");
        }
    }

    private String getDataWf(Component c) {
        // TODO : as optimization, i might want not to always spit this out???
        // it will add significantly to the payload.
        return WfUtil.getComponentId(c);
    }

    private boolean needsToBeWrapped(Component c) {
        return getConfig(c).isWrapHtmlOutput();
    }

    public void afterRender(Component c) {
        if (needsToBeWrapped(c)) {
            c.getResponse().write("</div>");
        }
    }

    @Override
    public String getAjaxRegionMarkupId(Component component) {
        return needsToBeWrapped(component) ?
                getWrappedId(component) : component.getMarkupId();
    }

    private String getWrappedId(Component component) {
        return component.getMarkupId()+"_w";
    }
}