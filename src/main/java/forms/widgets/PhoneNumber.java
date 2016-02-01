package forms.widgets;


import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.PhoneNumberConfig;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class PhoneNumber extends TextField<String> implements HasConfig<PhoneNumberConfig> {

    private final PhoneNumberConfig config;

    public PhoneNumber(String id, PhoneNumberConfig config) {
        super(id);
        setOutputMarkupId(true);
        this.config = config;
        add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (format()) {
                    target.add(PhoneNumber.this);
                }
            }
        });
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "text");
        super.onComponentTag(tag);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }

    protected boolean format() {
        String text = getDefaultModelObjectAsString();
        try {
            // TODO : config should pass locale & format
            Phonenumber.PhoneNumber phoneNumber = PhoneNumberUtil.getInstance().parse(text, config.getRegion());
            String result = PhoneNumberUtil.getInstance().format(phoneNumber, config.getFormat());
            setModelObject(result);
            return !result.equals(text.trim());
        } catch (NumberParseException e) {
            System.out.println("WARNING : " + text + " is not a valid phone number value");
            return false;
        }
    }

    @Override
    public PhoneNumberConfig getConfig() {
        return config;
    }

}
