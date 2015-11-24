package forms.widgets;

import forms.WidgetTypeEnum;
import forms.config.FormComponentConfig;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;

public class AddressConfig extends FormComponentConfig {

    public AddressConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.ADDRESS);
        withCss("");
    }

    @Override
    public Component create(String id) {
        return new Address(id, this);
    }
}
