package forms.widgets;

import demo.resources.Resource;
import forms.config.SelectPickerConfig;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;


public class SelectPicker extends DropDownChoice<String> {

//    private static final JavaScriptResourceReference EASY_SELECT_JS = new JavaScriptResourceReference(SelectPicker.class, "selectPicker.js");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.min.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");

    private AbstractDefaultAjaxBehavior ajaxHandler;

    public SelectPicker(String id, SelectPickerConfig config) {
        super(id, config.getChoices());
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
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("select");
        super.onComponentTag(tag);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(SELECT_JS));
        response.render(CssHeaderItem.forReference(SELECT_CSS));
    }

}
