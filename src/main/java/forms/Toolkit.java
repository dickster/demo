package forms;


import forms.config.FormConfig;

public interface Toolkit {


    public boolean isCustomWidgets();
    public void setCustomWidgets(boolean customWidgets);

    WidgetFactory createWidgetFactory(FormConfig formConfig);
    WidgetFactory createWidgetFactory();
    Theme getTheme();

}
