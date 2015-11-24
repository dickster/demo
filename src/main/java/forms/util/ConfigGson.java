package forms.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import forms.config.DontSendInJson;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class ConfigGson implements Serializable {

    public ConfigGson() {
    }

    public Gson getGson() {
        // TODO : skip empty collections/arrays[0]!

        ExclusionStrategy skipUnexposedFieldsStrategy = new ExclusionStrategy() {
            @Override public boolean shouldSkipField(FieldAttributes f) {
                return f.getAnnotation(DontSendInJson.class) != null;
            }
            @Override public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };


        JsonSerializer<Collection<?>> skipEmptyCollections = new JsonSerializer<Collection<?>>() {
            @Override
            public @Nullable JsonElement serialize(Collection <?> src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == null || src.isEmpty())
                    return null;

                JsonArray array = new JsonArray();

                for (Object child : src) {
                    JsonElement element = context.serialize(child);
                    array.add(element);
                }

                return array;
            }


        };
        JsonSerializer<Map<?,?>> skipEmptyMaps = new JsonSerializer<Map<?,?>>() {
            @Override
            public @Nullable JsonElement serialize(Map<?,?> src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == null || src.isEmpty())
                    return null;

                JsonObject obj = new JsonObject();

                for (Object key : src.keySet()) {
                    if (key!=null)
                        obj.add(key.toString(), context.serialize(src.get(key)));
                }

                return obj;
            }


        };
        return new GsonBuilder()
                .registerTypeHierarchyAdapter(Collection.class,skipEmptyMaps)
                .registerTypeHierarchyAdapter(Collection.class,skipEmptyCollections)
                .addSerializationExclusionStrategy(skipUnexposedFieldsStrategy)
                .create();
    }

    public String toJson(Object src) {
        return getGson().toJson(src);
    }
}
