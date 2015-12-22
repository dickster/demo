package forms.config;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import forms.HasWorkflow;
import forms.WidgetTypeEnum;
import forms.Workflow;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public abstract class Config<T extends Component & HasConfig> implements Serializable {

    private static final String CLASS = "class";
    private static final String PLUGIN_NA = "n/a";

    private @DontSendInJson Class<T> clazz;
    private @DontSendInJson GroupConfig parent;
    private @DontSendInJson Set<String> ajaxHandlers = Sets.newHashSet();
    private @DontSendInJson String prefix;
    private @DontSendInJson boolean wrapHtmlOutput = false;
    private @DontSendInJson boolean initiallyVisible = true;

    private String markupId;  // this is injected by the framework...don't set this yourself.

    private String id;
    private String type;
    private String property;
    private final String pluginName;
    private Map<String, String> attributes = Maps.newHashMap();
    // TODO : replace with a single object?
    private Map<String, Object> options = Maps.newHashMap();  // a place to store custom options.

    public Config(@Nonnull String property, @Nonnull String type, String pluginName) {
        this.property = property;
        this.id = property; // use property as id by default.
        this.type = type;
        this.pluginName = pluginName;
    }

    public Config(@Nonnull String property, @Nonnull String type) {
        this(property, type, PLUGIN_NA);
    }

    public Config(@Nonnull String property, WidgetTypeEnum type) {
        this(property, type.toString(), type.getPluginName());
    }

    public String getId() {
        return id;
    }

    public String getProperty() {
        return property;
    }

    // ALIAS for withId();
    public Config name(String name) {
        return withId(name);
    }

    public Config withId(String name) {
        this.id = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getCss() {
        return attributes.get(CLASS);
    }

    public Config withCss(String css) {
        withAttribute(CLASS, css);
        return this;
    }

    public Config<T> removeCss(String s) {
        String css = attributes.get(CLASS);
        if (css!=null) {
            attributes.put(CLASS, css.replace(s,""));
        }
        return this;
    }

    public Config removeAttribute(String attribute) {
        if (!attributes.containsKey(attribute)) {
            System.out.println("hmmm...you are trying to remove an attribute that doesn't exist : " + attribute);
        }
        attributes.remove(attribute);
        return this;
    }

    public Config withAttribute(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    public Config withAttribute(String key) {
        return withAttribute("");
    }

    public Config appendAttribute(String key, String value) {
        String v = attributes.get(key);
        if (v!=null) {
            attributes.put(key, Joiner.on(" ").join(v, value));
        }
        else {
            attributes.put(key, v);
        }
        return this;
    }

    public Config appendCss(String css) {
        appendAttribute(CLASS, css);
        return this;
    }

    public Map<String, String> getAttributes() {
        return ImmutableMap.copyOf(attributes);
    }

    public Map<String, Object> getOptions() {
        return ImmutableMap.copyOf(options);
    }

    public Config withOption(String key, Object value) {
        options.put(key, value);
        return this;
    }

    public String getPluginName() {
        return pluginName;
    }

    public Config<T> withMarkupId(String markupId) {
        this.markupId = markupId;
        return this;
    }

    public abstract T create(String id);

    protected final void post(@Nonnull Component component, @Nonnull Object event) {
        Workflow workflow = getWorkflow(component);
        workflow.post(event);
    }

    protected final @Nonnull Workflow getWorkflow(@Nonnull Component component) {
        HasWorkflow parent = component.findParent(HasWorkflow.class);
        if (parent==null) {
            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
        }
        return parent.getWorkflow();
    }

    public boolean isWrapHtmlOutput() {
        return wrapHtmlOutput;
    }

    public Config withWrappedHtmlOutput() {
        wrapHtmlOutput = true;
        return this;
    }

    public Set<String> getAjaxHandlers() {
        return ImmutableSet.copyOf(ajaxHandlers);
    }

    public Config withAjaxHandler(String handlerName) {
        ajaxHandlers.add(handlerName);
        return this;
    }

    // CAVEAT : if you use this, and you update the component via ajax, then you probably want to
    // call setOutputMarkupPlaceholderTag(true);
    public boolean isInitialyVisibile() {
        return initiallyVisible;
    }

    public Config initiallyVisible(boolean vis) {
        this.initiallyVisible = vis;
        return this;
    }

    public Config withParent(GroupConfig groupConfig) {
        this.parent = groupConfig;
        return this;
    }

    public GroupConfig getParent() {
        return parent;
    }

}

