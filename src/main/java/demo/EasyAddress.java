package demo;

import com.google.gson.GsonBuilder;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.ILabelProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import java.math.BigDecimal;

public class EasyAddress extends Panel implements ILabelProvider<String> {

    private static final String GOOGLE_MAPS_URL = "https://maps.googleapis.com/maps/api/js?sensor=false";
    private static final String CREATE_ADDRESS_JS = "easy.address.create(%s);";
    private static ResourceReference ADDRESS_JS = new JavaScriptResourceReference(EasyAddress.class, "address.js");
    
    private IModel<Address> model;
    private final TextField<String> text;
    private GpsLocation location;

    public EasyAddress(String id, IModel<Address> model) {
        super(id, model);
        this.model = model;
        setOutputMarkupId(true);
        
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
        response.render(OnDomReadyHeaderItem.forScript(String.format(CREATE_ADDRESS_JS, new GsonBuilder().create().toJson(getOptions()))));
    }

    protected Object getOptions() {
        return new Options();
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

    public EasyAddress setRequired(boolean required) {
        text.setRequired(required);
        return this;
    }

    public class Options {
        String id = "#"+EasyAddress.this.getMarkupId();
        Double latitude;
        Double longitude;

        Options() {
            if (EasyAddress.this.getApplication().getConfigurationType().equals(RuntimeConfigurationType.DEVELOPMENT) && location==null) {
                location = new GpsLocation(43.650713, -79.377683);
                }
            if (location!=null) {
                latitude = location.getLatitude().doubleValue();
                longitude = location.getLongitude().doubleValue();
            }
        }

    }


}
