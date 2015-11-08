package forms.config;


import forms.WidgetTypeEnum;

import javax.annotation.Nonnull;

public class ButtonConfig extends WidgetConfig {

    // add button options here.
    public ButtonConfig(@Nonnull String label) {
        super(label, WidgetTypeEnum.BUTTON);
    }
}
