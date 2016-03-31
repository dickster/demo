package forms.widgets.config;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.annotations.Expose;
import forms.HasWorkflow;
import forms.WidgetTypeEnum;
import forms.Workflow;
import forms.util.ConfigGson;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

// DB NOTE : these entities should be "detached" after they are read.
// you would never want to update these...only in editor.
public class Config<T extends Component & HasConfig> implements Serializable {

    private static final String CLASS = "class";
    private static final String PLUGIN_NA = "n/a";

    // use for serialization methods only...
    public transient String _class = getClass().getSimpleName();

    private Set<String> behaviors = Sets.newHashSet();
    private boolean wrapHtmlOutput;

    private @Expose Boolean hideInitially = null;
    private @Expose Map<Object, List<String>> dependents = Maps.newHashMap();

    // because two components can have the same property, we need two values.
    // ID which is (developer enforced) unique across the form, and property.
    // eg. two textFields could be bound to the same property "address.country", their id would have to be unique
    // values like "country1" & "country2".
    // Id's must be unique because in the DOM, this is the
    // value we use to find them.  (stuffed into data-wf attribute)
    private @Expose String id;
    private @Expose String property;
    private @Expose final Map<String, String> attributes = Maps.newHashMap();

    private @Expose String type;
    private @Expose String pluginName;
    // TODO : allow this to be customizable. for example, a simple json-friendly Object.  no map required.
    // template out construction of options & allow overrides for withOption()?
    // convert this to JsonObject..withOptions(JsonObject opts)
    private @Expose final Map<String, Object> options = Maps.newHashMap();  // a place to store custom options.

    private String tagName = null;
    private @Expose String selector;

    protected Config() {
        // for serialization purposes only.
    }

    public Config(@Nonnull String property, @Nonnull String type, String pluginName) {
        Preconditions.checkNotNull(property);
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
    @Deprecated
    public Config<T> name(String name) {
        return withId(name);
    }

    public Config<T> withId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getCss() {
        return attributes.get(CLASS);
    }

    public Config<T> withCss(String css) {
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

    public Config<T> withAttribute(String key, String value) {
        // TODO : should log if i'm overwriting attribute.
        attributes.put(key, value);
        return this;
    }

    public Config<T> withAttribute(String key) {
        return withAttribute(key, "");
    }

    public Config<T> appendAttribute(String key, String value) {
        String v = attributes.get(key);
        if (v!=null) {
            attributes.put(key, Joiner.on(" ").join(v, value));
        }
        else {
            attributes.put(key, value);
        }
        return this;
    }

    public Config<T> appendCss(String css) {
        appendAttribute(CLASS, css);
        return this;
    }

    public Map<String, String> getAttributes() {
        return ImmutableMap.copyOf(attributes);
    }

    public Map<String, Object> getOptions() {
        return ImmutableMap.copyOf(options);
    }

    public Config<T> withOption(String key, Object value) {
        options.put(key, value);
        return this;
    }

    // ALIAS.  more descriptive.   these options are sent to client. others are not.
    public Config withJsonOption(String key, Object value) {
        return withOption(key, value);
    }

    public String getPluginName() {
        return pluginName;
    }

    public Config<T> injectMarkupId(String markupId) {
        withOption("markupId", markupId);
        return this;
    }

    public T create(String id) {
        // note : i can't make this abstract for serialization reasons.
        // need to be able to instantiate this class.
        throw new UnsupportedOperationException("override this method!");
    }

    public void validate() {
        // override this if you want a chance to ensure your data is good before creating.
        // probably should check any spring bean names to see if they exist.
    }

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

    @Deprecated // i don't think i need this anymore.  i can use FormComponentPanel's to accomplish this (with some model shenanigans)
    public boolean isWrapHtmlOutput() {
        return wrapHtmlOutput;
    }

    @Deprecated
    public Config<T> withWrappedHtmlOutput() {
        wrapHtmlOutput = true;
        return this;
    }

    public Set<String> getBehaviors() {
        return ImmutableSet.copyOf(behaviors);
    }

    public Config withBehavior(String name) {
        behaviors.add(name);
        return this;
    }

    // CAVEAT : if you use this, and you update the component via ajax, then you probably want to
    // call setOutputMarkupPlaceholderTag(true);
    public Boolean isInitialyHidden() {
        return Boolean.TRUE.equals(hideInitially);
    }

    public Config<T> initiallyHidden() {
        this.hideInitially = true;
        return this;
    }

    public Component validateAndCreate(String id) {
        validate();
        return create(id);
    }

    // these methods assume you are using a checkbox/radio button which has only stores boolean values.
    //  for more control about which values are shown when, use other methods.
    public Config<T> withDependents(String... dependents) {
        return withDependents(Lists.newArrayList(dependents));
    }

    public Config<T> withDependents(List<String> dependents) {
        this.dependents.put(Boolean.TRUE, dependents);
        return this;
    }
    // -----------------

    public Config<T> withDependentsFor(Object key, String... dependents) {
        this.dependents.put(key, Lists.newArrayList(dependents));
        return this;
    }

    public void resetInitiallyHidden() {
        hideInitially = null;
    }

    public String getTagName() {
        return tagName;
    }

    public Config<T> withTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public Config withSelector(String selector) {
        this.selector =  selector;
        return this;
    }

    @Override
    public String toString() {
        // TODO : temporary way to generate string.
        // remove dependency on gson!
        return new ConfigGson().forSerialization().toJson(this);
    }

    protected String getOption(String key) {
        Object value = options.get(key);
        return value==null ? null : value.toString();
    }


}

