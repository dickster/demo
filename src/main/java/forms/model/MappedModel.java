package forms.model;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedModel extends CompoundPropertyModel<Map<String, Object>> {

    public MappedModel(Map<String, Object> object) {
        super(object);
    }

    public MappedModel() {
        super(new HashMap<String, Object>());
    }

    private void setObject(String property, Object object) {
        getObject().put(property, object);
    }

    private Object getObject(String property) {
        return getObject().get(property);
    }

    @Override
    public <C> IWrapModel<C> wrapOnInheritance(Component component) {
        return new MappedPropertyModel(this, component.getId());
    }

    public class MappedPropertyModel implements IWrapModel {
        private MappedModel model;
        private String propertyExpression;

        public MappedPropertyModel(MappedModel model, String propertyExpression) {
            this.model = model;
            this.propertyExpression = propertyExpression;
        }

        @Override
        public Object getObject() {
            return model.getObject(propertyExpression);
        }

        @Override
        public void setObject(Object object) {
            model.setObject(propertyExpression, object);
        }

        @Override
        public void detach() {

        }

        @Override
        public IModel<?> getWrappedModel() {
            return model;
        }
    }

    @Override
    public String toString() {
        String values = Joiner.on(", ").withKeyValueSeparator("->").join(getObject());
        return "MappedModel:{" + values + "}";
    }

    public JsonElement asJson() {
        JsonResult result = new JsonResult();
        Map<String, Object> map = getObject();

        for (String key:map.keySet()) {
            Object value = map.get(key);
            result.add(key, value);
        }
        return result.getJson();
    }



    class JsonResult {
        private JsonObject json = new JsonObject();
        private String property;
        private List<String> parent;

        JsonResult() {
        }
        public void add(String key, Object value) {
            List<String> properties = Lists.newArrayList(key.split("."));
            if (properties.size()>1) {
                parent = properties.subList(0,properties.size()-1);
                property = properties.get(properties.size()-1);
            }
            else {
                parent = Lists.newArrayList();
                property = properties.get(0);
            }
            add(parent, property, value);
        }

        private void add(List<String> parent, String property, Object value) {
            //getParent().addProperty(property, new Gson().toJsonTree(value));
        }

        private JsonObject getParent() {
            //blah blah blah..
            return new JsonObject();
        }


        private JsonElement jsonValue(Object value) {
            return new Gson().toJsonTree(value);
        }

        public JsonObject getJson() {
            return json;
        }
    }
}