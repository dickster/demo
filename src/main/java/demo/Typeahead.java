package demo;


import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.List;

public class Typeahead<T> extends TextField<T> {

    private static final String INIT_TYPEAHEAD_JS = "easy.typeahead.init(%s)";
    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Typeahead.class,"typeahead.js");

    private transient Gson gson = new Gson();

    private AbstractDefaultAjaxBehavior ajaxHandler;

    public Typeahead(String id) {
        super(id);
    }

    public Typeahead(String id, Class<T> type) {
        super(id, type);
    }

    public Typeahead(String id, IModel<T> model) {
        super(id, model);
    }

    public Typeahead(String id, IModel<T> model, Class<T> type) {
        super(id, model, type);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(ajaxHandler = createAjaxHandler());
    }

    public AbstractDefaultAjaxBehavior createAjaxHandler() {
        return new AbstractDefaultAjaxBehavior() {
            protected void respond(final AjaxRequestTarget target) {
                IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
                System.out.println(params.getParameterValue("query"));
                List<String> data = Lists.newArrayList("hello", "goodbye");
//                data.add(params.getParameterValue("query").toString());
                TextRequestHandler handler = new TextRequestHandler("application/json","UTF-8", convertToJson(data));
                RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
            }
        };
    }

    private String convertToJson(Object data) {
        return gson.toJson(data);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(TYPEAHEAD_JS));
        response.render(OnLoadHeaderItem.forScript(initTypeaheadJs()));
    }

    protected String initTypeaheadJs() {
        return String.format(INIT_TYPEAHEAD_JS, convertToJson(getOptions()));
    }

    public TypeAheadOptions getOptions() {
        return new TypeAheadOptions();
    }

    public class TypeAheadOptions {
        String id = Typeahead.this.getMarkupId();
        String remote =  ajaxHandler.getCallbackUrl() + "?query=%QUERY";
    }
}
