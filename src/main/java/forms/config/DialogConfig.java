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
        for (DialogSubmitButtonConfig config:configs) {
            buttons.add(config);
            config.setDialogId(getName());
        }
        return this;
    }

    public List<DialogSubmitButtonConfig> getButtons() {
        return buttons;
    }
}
