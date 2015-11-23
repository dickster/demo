package forms.widgets;

import forms.WidgetTypeEnum;
import forms.config.WidgetConfig;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;

public class AddressConfig extends WidgetConfig {

    public AddressConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.ADDRESS);
        withCss("");

    }

    @Override
    public Component create(String id) {
        return new Address(id, this);
    }
}
