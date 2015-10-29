package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.Map;


// refers to javascript/json options object that are sent to the browser.
public class JsonOptions {

    private WidgetTypeEnum type;
    private String id;
    // custom options that can be added on dynamically.  typically only used by implementation teams.
    //   all new brovada wide options should be added to this class as fields.
    private Map<String, Object> impl = Maps.newHashMap();

    public JsonOptions(String id, WidgetTypeEnum type) {
        this.id = id;
        this.type = type;
    }

    public JsonOptions withOption(String property, JsonElement value) {
        Preconditions.checkState(impl.get(property)!=null, "you are overriding the property " + property + " with " + value + " [previously " + impl.get(property) + "]");
        impl.put(property, value);
        return this;
    }

    public JsonOptions withOption(String property, String value) {
        return withOption(property, new JsonPrimitive(value));
    }

    public JsonOptions withOption(String property, Number value) {
        return withOption(property, new JsonPrimitive(value));
    }

    public JsonOptions withOption(String property, Character value) {
        return withOption(property, new JsonPrimitive(value));
    }

    public JsonOptions withOption(String property, Boolean value) {
        return withOption(property, new JsonPrimitive(value));
    }

    public JsonOptions withOption(String property, Object value) {
        return withOption(property, new Gson().toJsonTree(value));
    }

    public String asJson() {
        return new Gson().toJson(this);
    }

}

