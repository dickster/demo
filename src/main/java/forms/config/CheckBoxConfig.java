package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.CheckBox2;

public class CheckBoxConfig extends FormComponentConfig<CheckBox2> {

    private String label;

    public CheckBoxConfig(String property, String label) {
        super(property, WidgetTypeEnum.CHECKBOX);
        this.label = label;
        withCss("");
    }

    public String getLabel() {
        return label;
    }

    @Override
    public CheckBox2 create(String id) {
        return new CheckBox2(id, this);
    }
}
