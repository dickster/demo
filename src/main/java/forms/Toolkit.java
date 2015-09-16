package forms;


import com.google.common.eventbus.EventBus;

import javax.inject.Inject;

public class Toolkit {

    private @Inject Theme theme;
    private @Inject EventBus eventBus; /*prototype scoped */

    public WidgetFactory createWidgetFactory() {
        return new DefaultWidgetFactory().withEventBus(eventBus);
    }

    public Theme getTheme() {
        return theme;
    }
}
