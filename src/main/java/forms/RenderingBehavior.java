package forms;

import com.google.common.base.Preconditions;
import forms.util.ConfigGson;
import forms.util.WfUtil;
import forms.widgets.config.Config;
import forms.widgets.config.GroupConfig;
import forms.widgets.config.HasConfig;
import forms.widgets.config.HasTemplate;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.IAjaxRegionMarkupIdProvider;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.transformer.AbstractOutputTransformerContainer;
import org.apache.wicket.protocol.http.BufferedWebResponse;
import org.apache.wicket.request.cycle.RequestCycle;

import javax.inject.Inject;
import java.util.Map;

public class RenderingBehavior extends Behavior implements IAjaxRegionMarkupIdProvider {

    private static final String INIT_WIDGET_JS = "wf.widget(%s);";

    // TODO : allow extensions to this...
    // for example, additional business logic that will be delegated to.
    // e.g. a beforeRender logic that hides/shows component depending on model value.
    private @Inject ConfigGson gson;

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
        component.setOutputMarkupPlaceholderTag(false);
        addAttributes(component, tag);
    }

    private void addAttributes(Component component, ComponentTag tag) {
        if (!needsToBeWrapped(component)) {
            // this should EITHER be put on component markup OR it's wrapped parent's markup. (not both).
            Config config = getConfig(component);
            Map<String, String> attributes = config.getAttributes();
            for (String key:attributes.keySet()) {
                tag.getAttributes().put(key, attributes.get(key));
            }
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
            String div = String.format("<div id='%s' data-wf='%s' %s>",
                    getWrappedId(c),
                    getDataWf(c),
                    getWrappedAttributes(c));
            c.getResponse().write(div);
        }
    }

    private String getWrappedAttributes(Component c) {
        StringBuilder builder = new StringBuilder();
        Config config = getConfig(c);
        Map<String, String> attrs = config.getAttributes();
        for (String attr:attrs.keySet()) {
            String value = attrs.get(attr);
            builder.append(attr + "='" );
            builder.append(value+"' ");
        }
        return builder.toString();
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
        return component.getMarkupId()+"_wf";
    }

//    @Override
//    public CharSequence transform(Component component, CharSequence output) throws Exception {
//        // TODO : allow for transformations of html...
//        // eg. adding attributes, placeholder stuff?
//        return output;
//    }
}
