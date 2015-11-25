package forms.widgets;

import forms.config.Config;
import forms.config.DialogSubmitButtonConfig;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

public class DialogSubmitButton extends AjaxSubmitLink implements HasConfig {

    private @Inject WfUtil wfUtil;

    private final DialogSubmitButtonConfig config;

    public DialogSubmitButton(String id, DialogSubmitButtonConfig config) {
        super(id);
        add(new Label("label", Model.of(config.getName())));
        this.config = config;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        wfUtil.render(this, response);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        System.out.println("dialog submitted! (override this method to do something!");
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
    }

    @Override
    public Config getConfig() {
        return config;
    }

}
