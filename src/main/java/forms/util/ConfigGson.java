package forms.util;

import com.google.gson.*;
import forms.widgets.config.IncludeInJson;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ConfigGson implements Serializable {

    public ConfigGson() {
    }

    public Gson getGson() {
        ExclusionStrategy skipUnexposedFieldsStrategy = new ExclusionStrategy() {
            @Override public boolean shouldSkipField(FieldAttributes f) {
                IncludeInJson include = f.getAnnotation(IncludeInJson.class);
                return include==null || include.value()==false;
            }
            @Override public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        JsonSerializer<List<?>> skipEmptyLists = new JsonSerializer<List<?>>() {
            @Override
            public @Nullable JsonElement serialize(List <?> src, Type typeOfSrc, JsonSerializationContext context) {
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
                // should skip null values in maps too!
                .registerTypeHierarchyAdapter(Map.class,skipEmptyMaps)
                .registerTypeHierarchyAdapter(List.class,skipEmptyLists)
                .addSerializationExclusionStrategy(skipUnexposedFieldsStrategy)
                .create();
    }

    public String toJson(Object src) {
        return getGson().toJson(src);
    }
}
