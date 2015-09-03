package demo;


import com.google.gson.Gson;
import demo.resources.Resource;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class DateRangePicker extends TextField {

    private static final CssResourceReference DATERANGEPICKER_CSS = new CssResourceReference(Resource.class, "bootstrap-daterangepicker/daterangepicker-bs3.css");
    private static final JavaScriptResourceReference MOMENT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-daterangepicker/moment.min.js");
    private static final JavaScriptResourceReference DATERANGEPICKER_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-daterangepicker/daterangepicker.js");
    private static final String INIT_JS = "$('#%s').daterangepicker(%s);";

    public DateRangePicker(String id) {
        this(id, null);
    }

    public DateRangePicker(String id, IModel model) {
        super(id, model);
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        String options = new Gson().toJson(getOptions());
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_JS, getMarkupId(), options)));
    }

    protected Options getOptions() {
        return new Options();
    }

    class Options {
        String format ="YYYY/MM/DD";
        String startDate = "2013/01/01";
        String endDate =  "2013/01/31";
        String minDate = "2011/01/01";
        String maxDate =  "2015/12/31";
    }

}
