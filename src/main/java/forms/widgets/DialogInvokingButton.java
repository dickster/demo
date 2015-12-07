package forms.widgets;

import forms.config.Config;
import forms.config.DialogInvokingButtonConfig;
import forms.config.HasConfig;
import forms.util.ComponentFinder;
import forms.util.WfUtil;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

public class DialogInvokingButton extends Button implements HasConfig {

    private @Inject WfUtil wfUtil;

    private final DialogInvokingButtonConfig config;


    public DialogInvokingButton(String id, final DialogInvokingButtonConfig config) {
        super(id, Model.of(config.getName()));
        this.config = config;
        add(new AjaxEventBehavior("onclick") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                Dialog dialog = new ComponentFinder<Dialog>().find(getPage(), config.getDialogConfig().getName());
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
        wfUtil.render(this, response);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
