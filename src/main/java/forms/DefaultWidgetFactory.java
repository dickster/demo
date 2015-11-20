package forms;

import com.google.common.base.Preconditions;
import forms.config.ButtonConfig;
import forms.config.CheckBoxConfig;
import forms.config.DateLabelConfig;
import forms.config.DatePickerConfig;
import forms.config.LabelConfig;
import forms.config.SelectPickerConfig;
import forms.config.TextFieldConfig;
import forms.config.WidgetConfig;
import forms.widgets.CheckBoxPanel;
import forms.widgets.DatePanel;
import forms.widgets.IndicatingAjaxSubmitLink;
import forms.widgets.SelectPicker;
import forms.widgets.TextField2;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DefaultWidgetFactory extends WidgetFactory {

    public DefaultWidgetFactory() {
        super();
    }

    @Override
    public Component create(String id, WidgetConfig config) {
        Component basicComponent = createBasic(id, config);
        if (basicComponent!=null) {
            return basicComponent;
        }
        // handle any custom components types here...
        throw new IllegalArgumentException("widget type " + config.getWidgetType() + " is not supported.");
    }

    private @Nullable Component createBasic(String id, WidgetConfig config) {
        if (config instanceof TextFieldConfig) {
            return new TextField2(id);
        }
        if (config instanceof CheckBoxConfig) {
            return new CheckBoxPanel(id);
        }
        if (config instanceof LabelConfig) {
            return new Label(id,((LabelConfig)config).getText());
        }
        if (config instanceof DatePickerConfig) {
            return new DatePanel(id);
        }
        if (config instanceof DateLabelConfig) {
            return new Label(id); // TODO : add a timeago behavior to this.
        }
        if (config instanceof ButtonConfig) {
            return createAjaxButton(id, (ButtonConfig)config);
        }
        if (config instanceof SelectPickerConfig) {
            return new SelectPicker(id, (SelectPickerConfig)config);
        }
//        if (config instanceof SectionConfig) {
//            return new SectionPanel(id, (SectionConfig)config) {
//        }
        throw new IllegalArgumentException("widget type " + config.getClass().getSimpleName() + " is not supported.");
    }

    protected IndicatingAjaxSubmitLink createAjaxButton(final String id, final ButtonConfig config) {
        Preconditions.checkArgument(config.getName()!=null);
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
        Workflow<?> workflow = getWorkflow(component);
        workflow.post(event);
    }

    protected final @Nonnull Workflow<?> getWorkflow(@Nonnull Component component) {
        HasWorkflow parent = component.findParent(HasWorkflow.class);
        if (parent==null) {
            throw new IllegalStateException("uh oh, can't find workflow....this is not valid state of affairs!!");
        }
        return parent.getWorkflow();
    }
}
