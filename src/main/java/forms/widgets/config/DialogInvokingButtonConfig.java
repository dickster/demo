package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.widgets.DialogInvokingButton;

public class DialogInvokingButtonConfig extends FormComponentConfig<DialogInvokingButton> {

    private final DialogConfig dialogConfig;

    public DialogInvokingButtonConfig(String id, DialogConfig dialogConfig) {
        super(id, WidgetTypeEnum.DIALOG_INVOKING_BUTTON);
//        withCss("btn btn-default");
        this.dialogConfig = dialogConfig;
    }

    @Override
    public DialogInvokingButton create(String id) {
        return new DialogInvokingButton(id, this);
    }

    public DialogConfig getDialogConfig() {
        return dialogConfig;
    }
}
