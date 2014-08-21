package demo;


import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class EasyPhone extends TextField<String> {

    private final JavaScriptHeaderItem JS = JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(EasyPhone.class, "easyPhone.js"));
    private final AbstractDefaultAjaxBehavior behavior;

    private transient Gson gson = new Gson();

    private String INIT_JS = "easy.phone().init(%s);";
    private String UPDATE_JS = "document.getElementById('%s').phone.update('%s');";

    public EasyPhone(String id, IModel<String> model) {
        super(id, model);
        setOutputMarkupId(true);
        add(behavior = new AbstractDefaultAjaxBehavior() {
            @Override protected void respond(AjaxRequestTarget target) {
                IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
                String text = params.getParameterValue("text").toString();
                target.appendJavaScript(String.format(UPDATE_JS, getMarkupId(), format(text)));
            }
        });
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JS);
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_JS, gson.toJson(getOptions()))));
    }

    private EasyPhoneOptions getOptions() {
        return new EasyPhoneOptions();
    }

    protected String format(String text) {
        try {
           PhoneNumber phoneNumber = PhoneNumberUtil.getInstance().parse(text, "CA");
            System.out.println("trying to format number " + phoneNumber);
            return PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberFormat.NATIONAL);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return text;
    }

    public class EasyPhoneOptions {
        String id = EasyPhone.this.getMarkupId();
        String callback = behavior.getCallbackUrl().toString();

        public EasyPhoneOptions() {
        }
    }
}
