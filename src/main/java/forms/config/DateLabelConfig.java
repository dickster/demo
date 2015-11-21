package forms.config;

import forms.WidgetTypeEnum;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class DateLabelConfig extends WidgetConfig {

    // add timeago options here.
    public DateLabelConfig(String property) {
        super(property, WidgetTypeEnum.DATE_LABEL);
    }

    @Override
    public Component create(String id) {
        return new WebMarkupContainer(id);
    }


}
