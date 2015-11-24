package forms.widgets;

import demo.GpsLocation;
import forms.config.AddressConfig;
import forms.config.Config;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.ILabelProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import javax.inject.Inject;
import java.math.BigDecimal;

public class Address extends Panel implements ILabelProvider<String>, HasConfig {

    // TODO : write this as a plugin.

    private static final String GOOGLE_MAPS_URL = "https://maps.googleapis.com/maps/api/js?sensor=false";
    private static final String CREATE_ADDRESS_JS = "easy.address.create(%s);";
    private static ResourceReference ADDRESS_JS = new JavaScriptResourceReference(Address.class, "address.js");
    private final AddressConfig config;

    private TextField<String> text;
    private GpsLocation location;

    private @Inject WfUtil wfUtil;

    public Address(String id, AddressConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel model = getDefaultModel();

        // the wicket id's chosen here are chosen to reflect the json properties that are returned by the GoogleMaps API.
        // to help understand the javascript binding better.
        // see https://developers.google.com/maps/documentation/geocoding/#ReverseGeocoding
        add(new HiddenField<String>("administrative_area_level_1", new PropertyModel(model, "province")));
        add(new HiddenField<String>("locality", new PropertyModel(model, "city")));
        add(new HiddenField<String>("street_address", new PropertyModel(model, "streetAddress")));
        add(new HiddenField<String>("postal_code", new PropertyModel(model, "postalCode")));

        add(text = new TextField<String>("text", new PropertyModel(model, "formattedAddress")));
        add(new HiddenField<BigDecimal>("latitude", new PropertyModel(model, "gpsLocation.latitude")));
        add(new HiddenField<BigDecimal>("longitude", new PropertyModel(model, "gpsLocation.latitude")));
        add(new HiddenField<String>("country", new PropertyModel(model, "country")));

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forUrl(GOOGLE_MAPS_URL));
        response.render(JavaScriptReferenceHeaderItem.forReference(ADDRESS_JS));
        wfUtil.render(this, response);
//        response.render(OnDomReadyHeaderItem.forScript(String.format(CREATE_ADDRESS_JS, new GsonBuilder().create().toJson(getOptions()))));
    }

    @Override
    public IModel<String> getLabel() {
        return Model.of("address");
    }

    @Override
    public boolean isVisible() {
        if (super.isVisible()) {
            return get("text").isVisible();
        }
        return false;
    }

    public Address setRequired(boolean required) {
        text.setRequired(required);
        return this;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }


}
