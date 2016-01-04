package forms.widgets.config;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import forms.HasWorkflow;
import forms.WidgetTypeEnum;
import forms.Workflow;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

// DB NOTE : these entities should be "detached" after they are read.
// you would never want to update these...only in editor.
public abstract class Config<T extends Component & HasConfig> implements Serializable {

    private static final String CLASS = "class";
    private static final String PLUGIN_NA = "n/a";

    private GroupConfig parent;
    private Set<String> ajaxBehaviors = Sets.newHashSet();
    private boolean wrapHtmlOutput = false;
    private boolean initiallyVisible = true;

    // this is injected by the framework...don't set this yourself.
    private @IncludeInJson String markupId;
    private @IncludeInJson Boolean isAjax;
    private @IncludeInJson String templateId;

    private @IncludeInJson Map<Object, List<String>> dependents = Maps.newHashMap();


    // because two components can have the same property, we need two values.
    // ID which is (developer enforced) unique across the form, and property.
    // eg. two textFields could be bound to the same property "address.country", their id would have to be unique
    // values like "country1" & "country2".
    // Id's must be unique because in the DOM, this is the
    // value we use to find them.  (stuffed into data-wf attribute)
    private String id;
    private final String property;
    private final Map<String, String> attributes = Maps.newHashMap();

    private @IncludeInJson final String type;
    private @IncludeInJson final String pluginName;
    // TODO : allow this to be customizable. for example, a simple json-friendly Object.  no map required.
    // template out construction of options & allow overrides for withOption()?
    private @IncludeInJson final Map<String, Object> options = Maps.newHashMap();  // a place to store custom options.

    public Config(@Nonnull String property, @Nonnull String type, String pluginName) {
        Preconditions.checkNotNull(property);
        this.property = property;
        this.id = makeIdFrom(property); // use property as id by default.
        this.type = type;
        this.pluginName = pluginName;
    }

    private String makeIdFrom(String property) {
        return property;
//        // turn acord.policy.insuredOrPrincipal.name.first ---> apin.first
//        // what about []'s?  need to maintain these?
//        String[] tokens = property.split("\\.");
//        if (tokens.length==1) return property;
//        StringBuilder result = new StringBuilder();
//        for (int i=0;i<tokens.length-1;i++) {
//            result.append(tokens[i].toLowerCase().charAt(0));
//        }
//        result.append('.');
//        result.append(tokens[tokens.length - 1]);
//        return result.toString();
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

    public Config withId(String id) {
        this.id = id;
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

    // ALIAS.  more descriptive.   these options are sent to client. others are not.
    public Config withJsonOption(String key, Object value) {
        return withOption(key, value);
    }

    public String getPluginName() {
        return pluginName;
    }

    public Config<T> withMarkupId(String markupId) {
        this.markupId = markupId;
        return this;
    }

    public abstract T create(String id);

    public void validate() {
        // override this if you want a chance to ensure your data is good before creating.
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

    public boolean isWrapHtmlOutput() {
        return wrapHtmlOutput;
    }

    public Config withWrappedHtmlOutput() {
        wrapHtmlOutput = true;
        return this;
    }

    public Set<String> getAjaxBehaviors() {
        return ImmutableSet.copyOf(ajaxBehaviors);
    }

    public Config withAjaxBehavior(String name) {
        // check name.  if endsWith("ajaxBehavior") otherwise add it.
        // look for spelling errors.
        ajaxBehaviors.add(name);
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

    public void setIsAjax(boolean ajax) {
        this.isAjax = ajax;
    }

    public Component validateAndCreate(String id) {
        validate();
        return create(id);
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    // these methods assume you are using a checkbox/radio button which has only stores boolean values.
    //  for more control about which values are shown when, use other methods.
    public Config withDependents(String... dependents) {
        return withDependents(Lists.newArrayList(dependents));
    }

    public Config withDependents(List<String> dependents) {
        this.dependents.put(Boolean.TRUE, dependents);
        return this;
    }
    // -----------------


    public Config withDependents(Object key, String... dependents) {
        this.dependents.put(key, Lists.newArrayList(dependents));
        return this;
    }
}

