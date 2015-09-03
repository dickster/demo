package demo;

import demo.resources.Resource;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import javax.inject.Inject;
import java.io.Serializable;

public class SamplePage extends WebPage {

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.min.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference MULTISELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-multiselect.js");
    private static final CssResourceReference MULTISELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-multiselect.css");
    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/typeahead.bundle.js");
    private static final CssResourceReference TYPEAHEAD_CSS = new CssResourceReference(Resource.class,"bootstrap-3.1.1-dist/css/typeahead.bootstrap.css");

    private String foo = "boo@bar.com";
    private Address address = new Address();
    private TextField tf;
    private Label lbl;

    private @Inject SampleService sampleService;
    private EasyAddress addy;

    public SamplePage(final PageParameters parameters) {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Form("form")
                .add(tf = new TextField("text", new PropertyModel(this, "foo")))
                .add(addy = new EasyAddress("address", new PropertyModel(this, "address")))
                .add(lbl = new Label("label", Model.of("hello")))
        );
        lbl.setOutputMarkupId(true);
        tf.setOutputMarkupId(true);
        tf.add(new AjaxEventBehavior("onclick") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                System.out.println("click called!");
                lbl.setVisible(false);
                target.add(tf);
            }
        });
        tf.add(EmailAddressValidator.getInstance());

        String b = sampleService.helloWorld();
        System.out.println("dummy service : " + b);
        WebSession session = WebSession.get();
        Serializable user = session.getAttribute("custom-user");
        System.out.println(user);
        tf.setVisible(!user.equals("READ_ONLY"));
        lbl.setDefaultModelObject(b+"-serviceLabel");
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // TODO : add this to a resource bundle.
        response.render(JavaScriptReferenceHeaderItem.forReference(JQUERY_UI_JS));
        response.render(CssHeaderItem.forReference(JQUERY_UI_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(BOOTSTRAP_JS));
        response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(MULTISELECT_JS));
        response.render(CssHeaderItem.forReference(MULTISELECT_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(TYPEAHEAD_JS));
        response.render(CssHeaderItem.forReference(TYPEAHEAD_CSS));
    }

}
