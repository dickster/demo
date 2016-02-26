package forms.widgets.config;


import forms.WidgetTypeEnum;
import forms.widgets.WfButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

import javax.annotation.Nonnull;

public class ButtonConfig extends FormComponentConfig<WfButton> {

    public ButtonConfig(@Nonnull String label) {
        this(label, WidgetTypeEnum.BUTTON);
    }

    public ButtonConfig(@Nonnull String label, WidgetTypeEnum type) {
        super(label, type);
        withCss("btn btn-primary btn-submit");
    }

    @Override
    public WfButton create(String id) {
        return new WfButton(id, this);
    }

}
