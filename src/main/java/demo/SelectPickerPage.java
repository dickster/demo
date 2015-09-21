package demo;

import com.google.common.collect.Lists;
import demo.resources.Resource;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.List;

public class SelectPickerPage extends WebPage {

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");

    private String fruits;

    public SelectPickerPage(final PageParameters parameters) {
        List<String> options = Lists.newArrayList("apple", "orange", "banana");
        add(new SelectPicker("fruits", new PropertyModel<String>(this, "fruits"), options));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // TODO : add this to a resource bundle.
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
//        response.render(JavaScriptReferenceHeaderItem.forReference(JQUERY_UI_JS));
//        response.render(JavaScriptReferenceHeaderItem.forReference(LIBPHONENUMBER_JS));
//        response.render(CssHeaderItem.forReference(JQUERY_UI_CSS));
//        response.render(JavaScriptReferenceHeaderItem.forReference(JQUERY_UI_JS));
        response.render(JavaScriptReferenceHeaderItem.forReference(BOOTSTRAP_JS));
        response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));

        response.render(JavaScriptReferenceHeaderItem.forReference(SELECT_JS));
        response.render(CssHeaderItem.forReference(SELECT_CSS));

        //response.render(JavaScriptReferenceHeaderItem.forReference(EASY_SELECT_JS));

//        response.render(JavaScriptReferenceHeaderItem.forReference(MULTISELECT_JS));
//        response.render(CssHeaderItem.forReference(MULTISELECT_CSS));
//        response.render(JavaScriptReferenceHeaderItem.forReference(TYPEAHEAD_JS));
//        response.render(CssHeaderItem.forReference(TYPEAHEAD_CSS));
    }

}
