package forms;

import com.google.common.base.Preconditions;
import com.google.gson.*;
import com.google.gson.internal.Primitives;

import java.math.BigDecimal;

public class JsonModel extends NullSafeModel {

    public JsonModel() {
        super(new JsonObject());
        withPropertyManager(new JsonPropertyManager());
    }


    // --------------------------------------------------------------------------------------------

    class JsonPropertyManager implements IPropertyManager {

        @Override
        public IPropertyHolder createPropertyHolder(Object parent) {
            Preconditions.checkArgument(parent instanceof JsonObject);
            return new JsonPropertyHolder((JsonObject) parent);
        }
    }


    // --------------------------------------------------------------------------------------------

    class JsonPropertyHolder implements IPropertyHolder {

        private JsonObject target;

        public JsonPropertyHolder(JsonObject target) {
            this.target = target;
        }

        public <T> T set(String property, T value) {
            target.add(property, asJsonElement(value));
            return null;
        }

        private JsonElement asJsonElement(Object value) {
            if (value==null) {
                return JsonNull.INSTANCE;
            } else if (isPrimitive(value.getClass())) {
                Class<? extends Object> type = Primitives.wrap(value.getClass());
                if (type.isAssignableFrom(Number.class)) {
                    double dubble = ((Number) value).doubleValue();
                    return new JsonPrimitive(new BigDecimal(dubble));
                } else if (type.equals(String.class)) {
                    return new JsonPrimitive((String)value);
                }
            } else if (value.getClass().isArray()) {
                return asArray((Object[])value);
            } else {
                return new Gson().toJsonTree(value);
            }
            return null;
        }

        private JsonElement asArray(Object[] values) {
            JsonArray array = new JsonArray();
            for (Object value:values) {
                array.add(asJsonElement(value));
            }
            return array;
        }

        @Override
        public Object safeGet(String child) {
            Object result = get(child);
            // null check doesn't work. need to check for empty object?
            return result==null ?
                    createAndSet(child) :
                    result;
        }


        protected Object createAndSet(String property) {
            try {
                return set(property, JsonNull.INSTANCE);
            } catch (Exception oops) {
                throw new IllegalArgumentException("can't create an empty property for " + property + " in " + target.getClass().getSimpleName());
            }
        }

        protected boolean isPrimitive(Class<?> type) {
            return Primitives.isPrimitive(type) || Primitives.isWrapperType(type);
        }

        public Object get(final String property) {
            return target.get(property);
        }

    }


}
