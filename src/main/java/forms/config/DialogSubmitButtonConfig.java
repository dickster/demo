package forms.config;


import forms.WidgetTypeEnum;
import forms.widgets.DialogSubmitButton;

public class DialogSubmitButtonConfig extends FormComponentConfig<DialogSubmitButton> {

    private String dialogName;

    public DialogSubmitButtonConfig(String name) {
        super(name, WidgetTypeEnum.DIALOG_SUBMIT_BUTTON);
        withCss("btn");
    }

    @Override
    public DialogSubmitButton create(String id) {
        return new DialogSubmitButton(id, this);
    }

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    public String getDialogName() {
        return dialogName;
    }
}
