package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.CheckBox_;

public class CheckBoxConfig extends FormComponentConfig<CheckBox_> {

    private String label;

    public CheckBoxConfig(String property, String label) {
        super(property, WidgetTypeEnum.CHECKBOX);
        this.label = label;
        removeCss("form-control");
        withCss("checkbox");
    }

    public String getLabel() {
        return label;
    }

    @Override
    public CheckBox_ create(String id) {
        return new CheckBox_(id, this);
    }
}
