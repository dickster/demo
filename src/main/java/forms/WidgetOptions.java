package forms;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import demo.Name;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class WidgetOptions {

    private WidgetTypeEnum type;
    private Ajax ajax = null;
    private String id;
    // custom options that can be added on dynamically.  typically only used by implementation teams.
    //   all new brovada wide options should be added to this class as fields.
    private Map<String, Object> impl = Maps.newHashMap();

    public WidgetOptions(String id, WidgetTypeEnum type) {
        this.id = id;
        this.type = type;
    }

    public WidgetOptions withAjaxEvent(String event) {
        getAjax().registerEvent(event);
        return this;
    }

    public WidgetOptions withAjaxEvent(AjaxEventType event) {
        getAjax().registerEvent(event.name());
        return this;
    }

    public WidgetOptions withOption(String property, JsonElement value) {
        impl.put(property, value);
        return this;
    }

    public WidgetOptions withOption(String property, String value) {
        return withOption(property, new JsonPrimitive(value));
    }

    public WidgetOptions withOption(String property, Number value) {
        return withOption(property, new JsonPrimitive(value));
    }

    public WidgetOptions withOption(String property, Character value) {
        return withOption(property, new JsonPrimitive(value));
    }

    public WidgetOptions withOption(String property, Boolean value) {
        return withOption(property, new JsonPrimitive(value));
    }

    public WidgetOptions withOption(String property, Object value) {
        return withOption(property, new Gson().toJsonTree(value));
    }

    public String asJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public Ajax getAjax() {
        if (ajax == null) {
            ajax = new Ajax();
        }
        return ajax;
    }

    public class Ajax {
        private String callback;
        private List<String> events = Lists.newArrayList();
        private Boolean disabled;

        public void registerEvent(String event) {
            events.add(event);
        }
    }

    public class Theme {
        private String cssClass;
        private Map<String, String> data;
    }


}


/**
 *     private JsonObject impl = new JsonObject();

 public WidgetOptions(String id, WidgetTypeEnum type) {
 impl.addProperty("id", id);
 impl.addProperty("type", type.name());
 }

 public WidgetOptions withAjaxEvent(String event) {
 getAjaxEvents().add(new JsonPrimitive(event));
 return this;
 }

 private JsonArray getAjaxEvents() {
 JsonObject ajax = get(impl, "ajax");
 return getArray(ajax, "events");
 }

 private JsonObject get(JsonObject o, String property) {
 JsonObject result = (JsonObject) o.get(property);
 if (result==null) {
 result = new JsonObject();
 o.add(property, result);
 }
 return result;
 }

 private JsonArray getArray(JsonObject o, String property) {
 JsonArray result = (JsonArray) o.get(property);
 if (result==null) {
 result = new JsonArray();
 o.add(property, result);
 }
 return result;
 }

 public WidgetOptions withAjaxEvent(AjaxEventType event) {
 withAjaxEvent(event.name());
 return this;
 }

 public WidgetOptions withOption(String property, JsonElement value) {
 impl.add(property, value);
 return this;
 }

 public WidgetOptions withOption(String property, String value) {
 return withOption(property, new JsonPrimitive(value));
 }

 public WidgetOptions withOption(String property, Number value) {
 return withOption(property, new JsonPrimitive(value));
 }

 public WidgetOptions withOption(String property, Character value) {
 return withOption(property, new JsonPrimitive(value));
 }

 public WidgetOptions withOption(String property, Boolean value) {
 return withOption(property, new JsonPrimitive(value));
 }

 public WidgetOptions withOption(String property, Object value) {
 return withOption(property, new Gson().toJsonTree(value));
 }

 public String asJson() {
 // may need to register serializers here...
 return new Gson().toJson(impl);
 }


 // withOption("testing", new TestOption())
 class TestOption {
 private String id;
 private Foo foo = new Foo();
 private List<String> s = Lists.newArrayList("hello", "goodbye");
 }

 class Foo {
 private Date now;
 private Integer num;
 }

 */
