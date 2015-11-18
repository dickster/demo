package forms;


import forms.config.FormConfig;

public interface Toolkit {

    WidgetFactory createWidgetFactory(FormConfig formConfig);
    WidgetFactory createWidgetFactory();
    Theme getTheme();

}
