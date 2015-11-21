package forms.config;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import forms.WidgetTypeEnum;

import javax.annotation.Nonnull;
import java.util.Map;

public abstract class AbstractConfig implements Config {

    private static final String PLUGIN_NA = "n/a";

    private final String CLASS="class";

    // need annotations to figure out which options are json worthy.
    private final String pluginName;
    private String property;
    private Map<String, String> attributes = Maps.newHashMap();
    private String name;
    private String type;
    private Map options = Maps.newHashMap();  // a place to store custom options.

    public AbstractConfig(@Nonnull String property, @Nonnull String type, String pluginName) {
        this.property = property;
        this.name = property; // use property as name by default.
        this.type = type;
        this.pluginName = pluginName;
    }

    public AbstractConfig(@Nonnull String property, @Nonnull String type) {
        this(property, type, PLUGIN_NA);
    }

    public AbstractConfig(@Nonnull String property, WidgetTypeEnum type) {
        this(property, type.toString(), type.getPluginName());
    }

    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public String getWidgetType() {
        return type;
    }

    public <T extends AbstractConfig> T name(String name) {
        this.name = name;
        return (T) this;
    }

    public <T extends AbstractConfig> T withName(String name) {
        this.name = name;
        return (T) this;
    }

    public String getType() {
        return type;
    }

    public String getCss() {
        return attributes.get(CLASS);
    }

    public AbstractConfig withCss(String css) {
        withAttribute(CLASS, css);
        return this;
    }

    public <T extends AbstractConfig> T withAttribute(String key, String value) {
        attributes.put(key, value);
        return (T) this;
    }

    public <T extends AbstractConfig> T appendAttribute(String key, String value) {
        String v = attributes.get(key);
        attributes.put(key, Joiner.on(" ").join(v, value));
        return (T) this;
    }

    public AbstractConfig appendCss(String css) {
        appendAttribute(CLASS, css);
        return this;
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public Map<String, Object> getOptions() {
        return options;
    }

    public String getPluginName() {
        return pluginName;
    }
}

