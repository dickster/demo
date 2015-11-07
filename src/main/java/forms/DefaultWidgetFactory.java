package forms;

import forms.config.WidgetConfig;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import javax.annotation.Nullable;
import java.util.Date;

public class DefaultWidgetFactory extends WidgetFactory {

    public DefaultWidgetFactory() {
        super();
    }

    @Override
    public Component create(String id, WidgetConfig config, IModel<?> model) {
        Component basicComponent = createBasic(id, config, model);
        if (basicComponent!=null) {
            return basicComponent;
        }
        // handle any custom components types here...
        throw new IllegalArgumentException("widget type " + config.getWidgetType() + " is not supported.");
       // return new WebMarkupContainer(id);

//        if ("FOOBAR".equals(config.getWidgetType())) { return new CustomWidget(id); }
    }

    private @Nullable Component createBasic(String id, WidgetConfig config, IModel<?> model) {
        WidgetTypeEnum type = WidgetTypeEnum.valueOf(config.getWidgetType());
        if (type==null) {
            return null;
        }
        switch (type) {
            case TEXT_FIELD:
                return new TextField<String>(id);
            case CHECKBOX:
                return new CheckBox(id);
            case DATE:
                return new DateTextField(id);
            case DATE_LABEL:
                return new TextField<Date>(id);
            case LABEL:
                return new Label(id);
            case BUTTON:
                return createAjaxButton(id);
            default:
                throw new IllegalArgumentException("widget type " + type + " is not supported.");
        }
    }

    protected IndicatingAjaxSubmitLink createAjaxButton(final String id) {
        return new IndicatingAjaxSubmitLink(id) {
            @Override
            protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
                new WorkflowManager().post(form, new WfSubmitEvent(target, form));
            }
        };
    }
}
