package forms.widgets;

import forms.config.DialogButtonConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

public class DialogButton extends Button {

    private final DialogButtonConfig config;


    public DialogButton(String id, DialogButtonConfig config) {
        super(id, Model.of(config.getName()));
        this.config = config;
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "button");
        tag.getAttributes().put("data-toggle", "modal");
        tag.getAttributes().put("data-target", "#"+ config.getDialogName());
        super.onComponentTag(tag);
    }

}

