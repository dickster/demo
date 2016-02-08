package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.widgets.WfCheckBox;

public class CheckBoxConfig extends FormComponentConfig<WfCheckBox> {

    private @IncludeInJson String label;

    public CheckBoxConfig(String property, String label) {
        super(property, WidgetTypeEnum.CHECKBOX);
        this.label = label;
        removeCss("form-control");
        withCss("checkbox");
        //withWrappedHtmlOutput();
    }

    public String getLabel() {
        return label;
    }

    @Override
    public WfCheckBox create(String id) {
        return new WfCheckBox(id, this);
    }
}
