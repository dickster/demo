package forms.config;

import demo.GpsLocation;
import forms.WidgetTypeEnum;
import forms.widgets.Address;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;

public class AddressConfig extends FormComponentConfig {

    private GpsLocation location = new GpsLocation(43.650713, -79.377683);
    private Double latitude;
    private Double longitude;

    public AddressConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.ADDRESS);
        withCss("");
    }

    @Override
    public Component create(String id) {
        return new Address(id, this);
    }

    public AddressConfig withLocation(GpsLocation location) {
        this.location = location;
        return this;
    }


}
