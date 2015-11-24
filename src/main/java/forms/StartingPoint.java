package forms;

import demo.resources.Resource;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class StartingPoint extends WebPage {

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.min.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
//    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-multiselect.js");
    private static final CssResourceReference BOOTSTRAP_SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-multiselect.css");
    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/typeahead.bundle.js");
    private static final CssResourceReference TYPEAHEAD_CSS = new CssResourceReference(Resource.class,"bootstrap-3.1.1-dist/css/typeahead.bootstrap.css");

    private @SpringBean WfFactory workflowFactory;
    private @SpringBean Toolkit toolkit;

    private boolean customWidgets;

    public StartingPoint() {
        super(new PageParameters());

        add(new Form("form")
                .add(new CheckBox("customTheme", new PropertyModel(toolkit, "customTheme")))
                .add(new CheckBox("customWidgets", new PropertyModel(workflowFactory, "customWidgets")))
                .add(new AjaxSubmitLink("start") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                        FormBasedWorkflow wf = workflowFactory.create("commercial");
                        setResponsePage(new WfPage(wf));
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
