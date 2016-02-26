package forms.widgets.config;

import com.google.common.collect.Lists;
import forms.widgets.Dialog;

import java.util.List;

public class DialogConfig extends GroupConfig<Dialog> {

    private List<ButtonConfig> buttons = Lists.newArrayList();

    public DialogConfig(String id) {
        super(id);
        appendCss("modal");
    }

    public DialogConfig fade() {
        appendCss("fade");
        return this;
    }

    @Override
    public Dialog create(String id) {
        return new Dialog(id, this);
    }

    public DialogConfig withButtons(ButtonConfig... configs) {
        buttons.addAll(Lists.newArrayList(configs));
        return this;
    }

    public List<ButtonConfig> getButtons() {
        return buttons;
    }

//    public DialogInvokingButtonConfig createInvokingButtonConfig(String id) {
//        return new DialogInvokingButtonConfig(id, this);
//    }

    public DialogConfig withSubmitButton(String label) {
        buttons.add((ButtonConfig) new ButtonConfig(label).withCss("btn btn-primary"));
        return this;
    }

    public DialogConfig withOkButton() {
        return withSubmitButton("label.ok");
    }

    public DialogConfig withCancelButton(String label) {
        buttons.add((ButtonConfig) new ButtonConfig(label).withCss("btn btn-default"));
        return this;
    }

    public DialogConfig withCancelButton() {
        return withCancelButton("label.cancel");
    }
}
