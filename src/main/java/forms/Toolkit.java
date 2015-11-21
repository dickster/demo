package forms;


import forms.config.GroupConfig;

public interface Toolkit {


    public boolean isCustomWidgets();
    public void setCustomWidgets(boolean custom);

    public boolean isCustomTheme();
    public void setCustomTheme(boolean custom);

    WidgetFactory createWidgetFactory(GroupConfig formConfig);
    WidgetFactory createWidgetFactory();
    Theme getTheme();

}
