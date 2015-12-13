package forms.config;


import forms.WfDebugEvent;
import forms.widgets.WfButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import javax.annotation.Nonnull;

public class DebugRefreshButtonConfig extends ButtonConfig {

    private static final String DEBUG_REFRESH_BUTTON_ID = "$$DEBUG$$";

    public DebugRefreshButtonConfig(@Nonnull String label) {
        super(label);
        withCss("btn btn-success btn-large btn-debug");
    }

    @Override
    public WfButton create(String id) {
        return new WfButton(id, this) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                post(form, new WfDebugEvent(target, this));
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                post(form, new WfDebugEvent(target, this));
            }
        };
    }

}
