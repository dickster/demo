package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.DialogInvokingButton;

public class DialogInvokingButtonConfig extends FormComponentConfig<DialogInvokingButton> {

    private @DontSendInJson final DialogConfig dialogConfig;

    public DialogInvokingButtonConfig(String id, DialogConfig dialogConfig) {
        super(id, WidgetTypeEnum.DIALOG_BUTTON);
        withCss("btn");
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
