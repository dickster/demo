package forms;

import com.google.common.collect.Lists;
import demo.resources.Resource;
import forms.impl.PizzaModel;
import forms.model.GenericInsuranceObject;
import forms.util.IHello;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class StartingPoint extends WebPage {

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.min.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
//    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-multiselect.js");
    private static final CssResourceReference BOOTSTRAP_SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-multiselect.css");
    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/typeahead.bundle.js");
    private static final CssResourceReference TYPEAHEAD_CSS = new CssResourceReference(Resource.class,"bootstrap-3.1.1-dist/css/typeahead.bootstrap.css");

    private @SpringBean Toolkit toolkit;
    private @SpringBean IHello hello;

    private boolean customWidgets;
    private boolean invalidData = false;

    public StartingPoint() {
        super(new PageParameters());

        add(new Label("greeting", hello.greeting()));

        final GenericInsuranceObject obj = new GenericInsuranceObject();
        List<String> countries = Lists.newArrayList("Canada", "USA", "Mexico");

        add(new Form("form")
                .add(new DropDownChoice<String>("country", new PropertyModel(obj, "insured.country"), countries))
                .add(new CheckBox("customTheme", new PropertyModel(toolkit, "customTheme")))
                .add(new CheckBox("customWidgets", new PropertyModel(this, "customWidgets")))
                .add(new AjaxSubmitLink("simple") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        setResponsePage(new WfPage("test", obj));
                    }
                })
                .add(new AjaxSubmitLink("order") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        setResponsePage(new WfPage("pizza", new PizzaModel()));
                    }
                }));

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptReferenceHeaderItem.forReference(JQUERY_UI_JS));
//        response.render(CssHeaderItem.forReference(JQUERY_UI_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(BOOTSTRAP_JS));
        response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));

        response.render(CssHeaderItem.forReference(BOOTSTRAP_SELECT_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(SELECT_JS));

        response.render(JavaScriptReferenceHeaderItem.forReference(TYPEAHEAD_JS));
        response.render(CssHeaderItem.forReference(TYPEAHEAD_CSS));
    }
}
