package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

public class DefaultWidgetFactory extends WidgetFactory {

    public DefaultWidgetFactory() {
        super();
    }

    @Override
    public Component createWidget(String id, WidgetConfig config, IModel<?>... models) {
        switch (config.getWidgetType()) {
            case TEXT_FIELD:
            case CHECKBOX:
            case DATE:
            case LABEL:
            case RADIO_GROUP:
                break;
            case BUTTON:
                return new IndicatingAjaxSubmitLink(id) {
                    /**
                     * Override this method to provide special submit handling in a multi-button form. This method
                     * will be called <em>before</em> the form's onSubmit method.
                     */
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                    }

                    /**
                     * Override this method to provide special submit handling in a multi-button form. This method
                     * will be called <em>after</em> the form's onSubmit method.
                     */
                    @Override
                    protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onAfterSubmit(target, form);
                    }
                };
            default:
                break;
        }
        return new WebMarkupContainer(id);
    }
}
