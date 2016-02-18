package forms.widgets.config;

import com.google.gson.annotations.Expose;
import forms.WidgetTypeEnum;
import forms.widgets.WfCheckBox;

public class CheckBoxConfig extends FormComponentConfig<WfCheckBox> {

    private @Expose String label;

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
