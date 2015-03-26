package forms;


public class Toolkit {

    private Theme theme = new DefaultTheme();

    public WidgetFactory getWidgetFactory() {
        return new WidgetFactory();
    }

    public Theme getTheme() {
        return theme;
    }
}
