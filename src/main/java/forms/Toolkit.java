package forms;


import forms.config.FormConfig;

public interface Toolkit {


    public boolean isCustomWidgets();
    public void setCustomWidgets(boolean custom);

    public boolean isCustomTheme();
    public void setCustomTheme(boolean custom);

    WidgetFactory createWidgetFactory(FormConfig formConfig);
    WidgetFactory createWidgetFactory();
    Theme getTheme();

}
