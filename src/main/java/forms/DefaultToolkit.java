package forms;


import javax.inject.Inject;

public class DefaultToolkit implements Toolkit {

    private /*@Inject*/ Object sessionUser;  //
    private @Inject Theme theme;

    public WidgetFactory createWidgetFactory() {
        //depending on sessionUser, create appropriate widgetFactory.
        // may need to pass form,workflow, state info as well?
        return new DefaultWidgetFactory();
    }

    @Override
    public Theme getTheme() {
        return theme;
    }


}
