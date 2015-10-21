package demo;

import demo.resources.Resource;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;


public class GridstackPage extends WebPage {

    private static final JavaScriptResourceReference GRIDSTACK_JS = new JavaScriptResourceReference(Resource.class, "gridstack/gridstack.js");
    private static final CssResourceReference GRIDSTACK_CSS = new CssResourceReference(Resource.class, "gridstack/gridstack.css");
    private static final CssResourceReference GRIDSTACK_EXTRA_CSS = new CssResourceReference(Resource.class, "gridstack/gridstack-extra.css");
    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.11.4/jquery-ui.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.11.4/jquery-ui.css");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.min.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");

    public GridstackPage(final PageParameters parameters) {
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // TODO : add this to a resource bundle.
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptHeaderItem.forReference(JQUERY_UI_JS));
        response.render(CssHeaderItem.forReference(JQUERY_UI_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(BOOTSTRAP_JS));
        response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));

        response.render(JavaScriptReferenceHeaderItem.forReference(SELECT_JS));
        response.render(CssHeaderItem.forReference(SELECT_CSS));
        response.render(CssHeaderItem.forReference(GRIDSTACK_CSS));
        response.render(CssHeaderItem.forReference(GRIDSTACK_EXTRA_CSS));

        response.render(JavaScriptReferenceHeaderItem.forReference(GRIDSTACK_JS));

    }


}
