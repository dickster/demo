package forms.util;

import forms.config.Config;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;

public class WfUtil {
    public static String getComponentName(@Nonnull Component component) {
        return component.getMetaData(Config.NAME);
    }

    public static void setComponentName(@Nonnull Component component, @Nonnull String name) {
        component.setMetaData(Config.NAME, name);
    }
}
