package forms.widgets;

import forms.config.Config;
import forms.config.DialogInvokingButtonConfig;
import forms.config.HasConfig;
import forms.util.ComponentFinder;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

public class DialogInvokingButton extends Button implements HasConfig {

    private final DialogInvokingButtonConfig config;


    public DialogInvokingButton(String id, final DialogInvokingButtonConfig config) {
        super(id, Model.of(config.getId()));
        this.config = config;
        add(new AjaxEventBehavior("onclick") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                Dialog dialog = new ComponentFinder<Dialog>().find(getPage(), config.getDialogConfig().getId());
                dialog.show(target);
            }
        });
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "button");
        super.onComponentTag(tag);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}

