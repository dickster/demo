package forms.widgets.config;


import forms.WidgetTypeEnum;
import forms.widgets.WfButton;

import javax.annotation.Nonnull;

public class ButtonConfig extends FormComponentConfig<WfButton> {

    public ButtonConfig(@Nonnull String label) {
        super(label, WidgetTypeEnum.BUTTON);
        withCss("btn btn-primary btn-submit");
    }

    @Override
    public WfButton create(String id) {
        return new WfButton(id, this);
    }

}
