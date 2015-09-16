package forms;


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
