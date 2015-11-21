package forms.config;

import forms.WidgetTypeEnum;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class CheckBoxConfig extends WidgetConfig {

    public CheckBoxConfig(String property) {
        super(property, WidgetTypeEnum.CHECKBOX);
    }

    @Override
    public Component create(String id) {
        return new WebMarkupContainer(id);
    }
}
