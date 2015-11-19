package forms;


import forms.config.FormConfig;
import forms.config.TextFieldConfig;
import forms.config.WidgetConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.validation.validator.StringValidator;

import javax.inject.Inject;

public class DefaultToolkit implements Toolkit {

    private /*@Inject*/ Object sessionUser;  //
    private @Inject Theme theme;

    /*debug only*/ private boolean customWidgets;
    private boolean customTheme = true;

    @Override
    public WidgetFactory createWidgetFactory(FormConfig formConfig) {
        // example for debugging only.
        if (!customWidgets) {
            return createWidgetFactory();
        }

        if ("FORM-A".equals(formConfig.getName())) {
            return new DefaultWidgetFactory() {
                @Override
                public Component create(String id, WidgetConfig config) {
                    if ("name.first".equals(config.getName())) {
                        config.withCss("bg-primary");
                        return new Label(id, config.getName() + " (custom widget)");
                    }
                    return super.create(id, config);
                }
            };
        }
        else if ("FORM-A-relayout".equals(formConfig.getName())) {
            return new DefaultWidgetFactory() {
                @Override
                public Component create(String id, WidgetConfig config) {
                    if (config instanceof TextFieldConfig) {
                        Component c = super.create(id, config);
                        if (c instanceof FormComponent) {
                            c.add(new StringValidator(1,5));
                            ((FormComponent)c).setRequired(true);
                        }
                    }
                    config.withCss("bg-success");
                    return super.create(id, config);
                }
            };
        }
        else {
            return createWidgetFactory();
        }
    }

    public WidgetFactory createWidgetFactory() {
        //depending on sessionUser, create appropriate widgetFactory.
        // may need to pass form,workflow, state info as well?
        return new DefaultWidgetFactory();
    }

    @Override
    public Theme getTheme() {
        if (isCustomTheme()) {
            return new CustomTheme();
        }
        return theme;
    }


public boolean isCustomWidgets() {
    return customWidgets;
}

public void setCustomWidgets(boolean customWidgets) {
    this.customWidgets = customWidgets;
}

public boolean isCustomTheme() {
    return customTheme;
}

public void setCustomTheme(boolean custom) {
    this.customTheme = custom;
}



}
