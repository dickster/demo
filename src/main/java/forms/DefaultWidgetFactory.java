package forms;

import forms.widgets.config.Config;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;

public class DefaultWidgetFactory extends WidgetFactory {

    public DefaultWidgetFactory() {
        super();
    }

    @Override
    public @Nonnull Component create(String id, @Nonnull Config config) {
        return config.validateAndCreate(id);
    }

}
