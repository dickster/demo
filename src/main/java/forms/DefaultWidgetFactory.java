package forms;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

public class DefaultWidgetFactory extends WidgetFactory {
    @Override
    public Component createWidget(String id, WidgetConfig config, IModel<?>... models) {
        switch (config.getWidgetType()) {
            case TEXT_FIELD:
            case CHECKBOX:
            case DATE:
            case LABEL:
            case RADIO_GROUP:
                break;
            default:
                break;
        }
        return new WebMarkupContainer(id);
    }
}
