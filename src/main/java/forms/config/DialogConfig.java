package forms.config;

import com.google.common.collect.Lists;
import forms.widgets.Dialog;

import java.util.List;

public class DialogConfig extends GroupConfig<Dialog> {

    private List<DialogSubmitButtonConfig> buttons = Lists.newArrayList();

    public DialogConfig(String id) {
        super(id);
    }

    @Override
    public Dialog create(String id) {
        return new Dialog(id, this);
    }

    public DialogConfig withButtons(DialogSubmitButtonConfig... configs) {
        int i = 0;
        for (DialogSubmitButtonConfig config:configs) {
            config.appendCss(i==0 ? "btn-primary" :"btn-default");
            buttons.add(config);
            config.setDialogName(getId());
            i++;
        }
        return this;
    }

    public List<DialogSubmitButtonConfig> getButtons() {
        return buttons;
    }

    public DialogInvokingButtonConfig createInvokingButtonConfig(String id) {
        return new DialogInvokingButtonConfig(id, this);
    }
}
