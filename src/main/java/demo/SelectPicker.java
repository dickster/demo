package demo;

import com.google.gson.Gson;
import demo.resources.Resource;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.ArrayList;


public class SelectPicker<T> extends DropDownChoice<T> {

    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");
    private static final JavaScriptResourceReference EASY_SELECT_JS = new JavaScriptResourceReference(SelectPicker.class, "selectPicker.js");


    private static final String INIT = "easy.selectPicker.init(%s);";
    private AbstractDefaultAjaxBehavior ajaxHandler;

    public SelectPicker(String id, IModel<T> model) {
        super(id, model, new ArrayList<T>());
        setOutputMarkupId(true);
        //add(ajaxHandler = createAjaxHandler());
    }

    public AbstractDefaultAjaxBehavior createAjaxHandler() {
        return new AbstractDefaultAjaxBehavior() {
            protected void respond(final AjaxRequestTarget target) {
                IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
//                TextRequestHandler handler = new TextRequestHandler("application/json","UTF-8", new Gson().toJson("hello"));
                TextRequestHandler handler = new TextRequestHandler("application/json","UTF-8", "{'1':'hello','2':'goodbye'}");
                RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
               // NOTE : a filter should be created to cache these responses.
            }
        };
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptReferenceHeaderItem.forReference(SELECT_JS));
        response.render(CssHeaderItem.forReference(SELECT_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(EASY_SELECT_JS));
        String optionsJson = new Gson().toJson(getOptions());
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT, optionsJson)));
    }

    private SelectPickerOptions getOptions() {
        return new SelectPickerOptions();
    }

    public class SelectPickerOptions {
       // String url = ajaxHandler.getCallbackUrl().toString();
        String id = getMarkupId();
    }
}
