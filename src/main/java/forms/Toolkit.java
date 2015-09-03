package forms;


import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;

public class Toolkit {

    private @Inject Theme theme;

    public WidgetFactory createWidgetFactory() {
        return new DefaultWidgetFactory();
    }

    public Theme getTheme() {
        return theme;
    }
}
