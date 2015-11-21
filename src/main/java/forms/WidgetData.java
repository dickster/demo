package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import forms.config.Config;
import org.apache.wicket.Component;

import java.io.Serializable;
import java.util.Map;


// refers to javascript/json options object that are sent to the browser.
public class WidgetData implements Serializable {

    private String name;
    private String type;
    private String id;
    private String pluginName;
    private Object pluginOptions;
    private Map<String, String> attributes = Maps.newHashMap();
    // note : get gson to skip empty maps.
    private Map<String, Object> customOptions = Maps.newHashMap();

    public WidgetData(String id, WidgetTypeEnum type) {
        this.id = id;
        this.type = type.toString().toLowerCase();
    }

    public WidgetData(Component component, Config config) {
        this.id = component.getMarkupId();
        this.type = config.getType();
        this.name = config.getName();
        this.attributes = config.getAttributes();
    }

    public WidgetData withCustomOption(String property, Object value) {
        Preconditions.checkState(customOptions.get(property)!=null, "you are overriding the property " + property + " with " + value + " [previously " + customOptions.get(property) + "]");
        customOptions.put(property, value);
        return this;
    }

    public WidgetData withPluginOptions(Object options) {
        this.pluginOptions = options;
        return this;
    }

    public WidgetData withPluginName(String pluginName) {
        this.pluginName = pluginName;
        return this;
    }
}

