package forms.widgets;

import forms.config.Config;
import forms.config.DialogButtonConfig;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

public class DialogButton extends Button implements HasConfig {

    private @Inject WfUtil wfUtil;

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

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        wfUtil.render(this, response);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}

