package forms;


public class DefaultToolkit implements Toolkit {

    private /*@Inject*/ Object sessionUser;  //
    public WidgetFactory createWidgetFactory() {
        //depending on sessionUser, create appropriate widgetFactory.
        // may need to pass form,workflow, state info as well?
        return new DefaultWidgetFactory();
    }


}
