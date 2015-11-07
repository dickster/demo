package forms.config;

import forms.WidgetTypeEnum;

public class CheckBoxConfig extends WidgetConfig {

    public CheckBoxConfig(String property, String label) {
        super(property, label, WidgetTypeEnum.CHECKBOX);
    }

}
