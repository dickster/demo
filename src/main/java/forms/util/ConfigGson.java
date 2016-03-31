package forms.util;

import com.google.gson.*;
import forms.widgets.config.*;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigGson implements Serializable {

    public ConfigGson() {
    }

    /**
     * when rendering, we only want to serialize the data that the .js side
     * needs. this means only stuff annotated with @Expose.
     */
    public Gson forRendering() {

        // TODO : blargh.  these skip...Strategy's don't work with adapterFactory (used for polymorphic support).
        //   not sure why. everything works when i do not include the skipLists.
        // but when i use it, the "_class" field doesn't get serialized.
        // i suspect the underlying use of array.add(context.serialize(blah)) doesn't take into account the
        //  type factory code??

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
        JsonSerializer<Set<?>> skipEmptySets = new JsonSerializer<Set<?>>() {
            @Override
            public @Nullable JsonElement serialize(Set <?> src, Type typeOfSrc, JsonSerializationContext context) {
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
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeHierarchyAdapter(Set.class,skipEmptySets)
                .registerTypeHierarchyAdapter(Map.class,skipEmptyMaps)
                .registerTypeHierarchyAdapter(List.class, skipEmptyLists)
                .create();
    }


    public Gson forSerialization() {
        RuntimeTypeAdapterFactory<Config> configAdapter =
                RuntimeTypeAdapterFactory.of(Config.class, "_class")
                        .registerSubtype(FormConfig.class)
                        .registerSubtype(GroupConfig.class)
                        .registerSubtype(CheckBoxConfig.class)
                        .registerSubtype(YesNoConfig.class)
                        .registerSubtype(LabelConfig.class)
                        .registerSubtype(DialogConfig.class)
                        .registerSubtype(DialogButtonConfig.class)
                        .registerSubtype(FeedbackPanelConfig.class)
                        .registerSubtype(SelectPickerConfig.class)
                        .registerSubtype(TextFieldConfig.class)
                        .registerSubtype(ButtonConfig.class);

        return new GsonBuilder()
                .registerTypeAdapterFactory(configAdapter)
                .create();
    }

}
