package demo;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class VinTextField extends Panel {

    private static final JavaScriptResourceReference JS = new JavaScriptResourceReference(VinTextField.class,"vin.js");
    private static final CssResourceReference CSS = new CssResourceReference(VinTextField.class,"vin.css");
    private static final String INIT_JS = "easy.vin().init(%s)";
    private static final String UPDATE_JS = "document.getElementById('%s').vin.update('%s');";

    private Map<String,VIN> vins = Maps.newHashMap();
    private AbstractDefaultAjaxBehavior behavior;
//    private Component overlay;

    public VinTextField(String id, IModel<VIN> model) {
        super(id, model);
        add(new AttributeAppender("class", "vin"));
        setOutputMarkupId(true);
        add(new TextField("text", new PropertyModel(model, "formattedText")));
        add(new HiddenField<String>("vin", new PropertyModel(model, "vin")));
        add(new HiddenField<Integer>("year", new PropertyModel(model, "year")));
        add(new HiddenField<String>("manufacturer", new PropertyModel(model, "manufacturer")));
        add(new HiddenField<String>("model", new PropertyModel(model, "model")));
//        add(overlay = new Label("overlay", Model.of("nothing")).setVisible(false).setOutputMarkupPlaceholderTag(true).setOutputMarkupId(true));
        add(behavior = new AbstractDefaultAjaxBehavior() {
            @Override
            protected void respond(AjaxRequestTarget target) {
                IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
                String text = params.getParameterValue("text").toString();
                suggestVin(text, target);
            }
        });
        addVin(new VIN(2014, "Ford", "Tarus", "ABC2014"));
        addVin(new VIN(2014, "Volkswagen", "Bug", "BUG2014"));
        addVin(new VIN(2014, "GMC", "PickupTruck", "GMC2014"));
        addVin(new VIN(2013, "GMC", "PickupTruck", "GMC2013"));
        addVin(new VIN(2013, "GMC", "PickupTruck", "GMC2013"));
        addVin(new VIN(1972, "Ford", "Pinto", "PNT1972"));
        addVin(new VIN(2009, "Toyota", "Camry", "CAM2009"));
    }

    private void addVin(VIN vin) {
        vins.put(vin.getVin(), vin);
    }

    protected void suggestVin(String text, AjaxRequestTarget target) {
        VIN vin = getVin(text);
        target.appendJavaScript(String.format(UPDATE_JS,getMarkupId(),vin==null ? "" : vin.description()));
    }

    private VIN getVin(String text) {
        return vins.get(text.trim());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(JS));
        response.render(CssHeaderItem.forReference(CSS));
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_JS, new Gson().toJson(new AutoCompleteOptions()))));
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
    }

    public class AutoCompleteOptions implements Serializable {
        String id = VinTextField.this.getMarkupId();
        String callback = behavior.getCallbackUrl().toString();
        List<String> options;
    }

}
