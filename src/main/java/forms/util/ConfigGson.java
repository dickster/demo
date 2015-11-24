package forms.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forms.config.DontSendInJson;

import java.io.Serializable;

public class ConfigGson implements Serializable {

    public ConfigGson() {
    }

    public Gson getGson() {
        ExclusionStrategy skipUnexposedFieldsStrategy = new ExclusionStrategy() {
            @Override public boolean shouldSkipField(FieldAttributes f) {
                return f.getAnnotation(DontSendInJson.class) != null;
            }
            @Override public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        return new GsonBuilder()
                .addSerializationExclusionStrategy(skipUnexposedFieldsStrategy)
                .create();
    }

    public String toJson(Object src) {
        return getGson().toJson(src);
    }
}
