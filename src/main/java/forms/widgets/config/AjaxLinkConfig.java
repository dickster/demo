package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.spring.WfNavigator;
import forms.widgets.WfAjaxLink;
import forms.widgets.WfButton;
import org.apache.wicket.ajax.AjaxRequestTarget;

import javax.annotation.Nonnull;

public abstract class AjaxLinkConfig<T extends WfAjaxLink> extends FormComponentConfig<T> {

    private DialogConfig dialogConfig;

    public AjaxLinkConfig(@Nonnull String label) {
        this(label, WidgetTypeEnum.BUTTON);
    }

    public AjaxLinkConfig(@Nonnull String label, WidgetTypeEnum type) {
        super(label, type);
    }

    @Override
    public abstract T create(String id);
}
