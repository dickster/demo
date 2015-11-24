package forms.config;


import com.google.common.base.Preconditions;
import forms.HasWorkflow;
import forms.WfSubmitErrorEvent;
import forms.WfSubmitEvent;
import forms.WidgetTypeEnum;
import forms.Workflow;
import forms.widgets.IndicatingAjaxSubmitLink;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import javax.annotation.Nonnull;

public class ButtonConfig extends FormComponentConfig {

    // add button options here.
    public ButtonConfig(@Nonnull String label) {
        super(label, WidgetTypeEnum.BUTTON);
        withCss("btn btn-primary");
    }

    @Override
    public Component create(String id) {
        return createAjaxButton(id, this);
    }


    protected IndicatingAjaxSubmitLink createAjaxButton(final String id, final ButtonConfig config) {
        Preconditions.checkArgument(config.getName() != null);
        return new IndicatingAjaxSubmitLink(id, config.getName()) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
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

    protected final void post(@Nonnull Component component, @Nonnull Object event) {
        Workflow workflow = getWorkflow(component);
        workflow.post(event);
    }

    protected final @Nonnull Workflow getWorkflow(@Nonnull Component component) {
        HasWorkflow parent = component.findParent(HasWorkflow.class);
        if (parent==null) {
            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
        }
        return parent.getWorkflow();
    }

}
