package forms.config;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import forms.WidgetTypeEnum;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;

public abstract class Config<T extends Component & HasConfig> implements Serializable {

    private static final String CLASS = "class";
    private static final String PLUGIN_NA = "n/a";

    private String name; // TODO : refactor this. change name to "ID".
    private String type;
    private String property;
    private final String pluginName;
    private Map<String, String> attributes = Maps.newHashMap();
    private Map<String, Object> options = Maps.newHashMap();  // a place to store custom options.

    public Config(@Nonnull String property, @Nonnull String type, String pluginName) {
        this.property = property;
        this.name = property; // use property as name by default.
        this.type = type;
        this.pluginName = pluginName;
    }

    public Config(@Nonnull String property, @Nonnull String type) {
        this(property, type, PLUGIN_NA);
    }

    public Config(@Nonnull String property, WidgetTypeEnum type) {
        this(property, type.toString(), type.getPluginName());
    }



    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public Config name(String name) {
        this.name = name;
        return this;
    }

    public Config withName(String name) {
        this.name = name;
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

    public Config withAttribute(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    public Config withAttribute(String key) {
        return withAttribute("");
    }

    public Config appendAttribute(String key, String value) {
        String v = attributes.get(key);
        attributes.put(key, Joiner.on(" ").join(v, value));
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

//    public void setPluginName(String pluginName) {
//        this.pluginName = pluginName;
//    }
    public String getPluginName() {
        return pluginName;
    }


    public abstract T create(String id);
}

