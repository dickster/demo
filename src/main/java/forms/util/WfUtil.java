package forms.util;

import forms.config.Config;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WfUtil {

    public static @Nullable String getComponentName(@Nonnull Component component) {
        Config config = component.getMetaData(Config.KEY);
        return config==null ? null : config.getName();
    }

    public static String getComponentProperty(@Nonnull Component component) {
        Config config = component.getMetaData(Config.KEY);
        return config.getProperty();
    }

   // TODO : put *post* method here.

}
