package forms.widgets;

import com.google.gson.Gson;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;

import java.util.List;


public class SelectPicker extends DropDownChoice<String> {

//    private static final JavaScriptResourceReference EASY_SELECT_JS = new JavaScriptResourceReference(SelectPicker.class, "selectPicker.js");

    private static final String INIT = "$('.selectpicker').selectpicker();";
    private AbstractDefaultAjaxBehavior ajaxHandler;

    public SelectPicker(String id, IModel<String> model, List<String> options) {
        super(id, model, options );
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
