package forms;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

public class WidgetFactory {

    public WidgetFactory(/**user, locale, settings, permissions*/) {
    }

    public Component create(WidgetConfig config, IModel<?> formModel) {
        switch (config.getWidgetType()) {
            case TEXT_FIELD:
            default:
                return new TextField(config.getProperty(), createModel(config, formModel));
        }
    }

    protected IModel<?> createModel(WidgetConfig config, IModel<?> formModel) {
        String propertyExpression = config.getProperty();
        return null;
        //return createModelForId(propertyExpression);
    }

}
