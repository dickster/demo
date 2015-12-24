package forms.widgets.config;


import com.google.common.base.Preconditions;
import forms.WfSubmitErrorEvent;
import forms.WfSubmitEvent;
import forms.WidgetTypeEnum;
import forms.widgets.WfButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import javax.annotation.Nonnull;

public class ButtonConfig extends FormComponentConfig<WfButton> {

    public ButtonConfig(@Nonnull String label) {
        super(label, WidgetTypeEnum.BUTTON);
        withCss("btn btn-primary btn-submit");
    }

    @Override
    public WfButton create(String id) {
        return createAjaxButton(id, this);
    }

    protected WfButton createAjaxButton(final String id, final ButtonConfig config) {
        Preconditions.checkArgument(config.getId() != null);
        return new WfButton(id, config) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form <?> form) {
                //refactor this into this class...don't need to post anywhere else.
                post(form, new WfSubmitEvent(target, this, form));
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                //refactor this into this class...don't need to post anywhere else.
                post(form, new WfSubmitErrorEvent(target, this, form));
            }
        };
    }

}
