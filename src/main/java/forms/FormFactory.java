package forms;

public class FormFactory /*implements ApplicationContextAware */{

    //ApplicationContext context;
    public DynamicForm getForm(String name) {
        //look up in spring context...otherwise, if in debug mode search home folders for a form config with matching name.
        return new DynamicForm("foo");
    }

    public DynamicForm getForm(FormConfig config) {
        //look up in spring context...otherwise, if in debug mode search home folders for a form config with matching name.
        return new DynamicForm("foo");
    }


    // parallel class needed for panelFactory.  they both should extend IHasWidgetsFactory.  forms should extend panels? in the config sense.
}
