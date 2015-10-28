package forms;


import javax.inject.Inject;

public class Toolkit {

    private @Inject Theme theme;
    private @Inject WidgetFactory widgetFactory;

    public Theme getTheme() {
        return theme;
    }

    public WidgetFactory getWidgetFactory() {
        return widgetFactory;
    }
}
