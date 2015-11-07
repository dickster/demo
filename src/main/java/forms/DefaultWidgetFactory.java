package forms;

import forms.config.WidgetConfig;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;

public class DefaultWidgetFactory extends WidgetFactory {

    private @Inject
    WorkflowManager manager;

    public DefaultWidgetFactory() {
        super();
    }

    @Override
    public Component create(String id, WidgetConfig config, IModel<?> model) {
        switch (config.getWidgetType()) {
            case TEXT_FIELD:
                return new TextField<String>(id);
            case CHECKBOX:
                return new CheckBox(id);
            case DATE:
                return new DateTextField(id);
            case LABEL:
                return new Label(id);
            case BUTTON:
                return createAjaxButton(id);
            default:
                break;
        }
        return new WebMarkupContainer(id);
    }

    protected IndicatingAjaxSubmitLink createAjaxButton(final String id) {
        return new IndicatingAjaxSubmitLink(id) {
            @Override
            protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
                manager.post(form, new WfSubmitEvent(target, form));
            }
        };
    }
}
