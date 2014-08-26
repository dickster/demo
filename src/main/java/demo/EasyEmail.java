package demo;

import com.google.gson.Gson;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public class EasyEmail extends EmailTextField {

    private static final JavaScriptResourceReference JS = new JavaScriptResourceReference(EasyEmail.class,"email.js");
    private static final String INIT_JS = "easy.email().init(%s)";

    public EasyEmail(String id, IModel<String> model) {
        super(id, model);
        setOutputMarkupId(true);
        add(EmailAddressValidator.getInstance());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(JS));
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_JS, new Gson().toJson(getOptions()))));
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
    }


    protected EmailOptions getOptions() {
        return new EmailOptions();
    }

    public class EmailOptions {
        String id = EasyEmail.this.getMarkupId();
    }

}
