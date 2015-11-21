package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.TextField2;
import org.apache.wicket.Component;

public class TextFieldConfig extends WidgetConfig {

    public TextFieldConfig(String property) {
        super(property, WidgetTypeEnum.TEXT_FIELD);
    }

    @Override
    public Component create(String id) {
        return new TextField2(id, this);
    }


}
