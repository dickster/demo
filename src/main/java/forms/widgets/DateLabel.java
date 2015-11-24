package forms.widgets;

import com.google.gson.Gson;
import demo.resources.Resource;
import forms.config.Config;
import forms.config.DateLabelConfig;
import forms.config.HasConfig;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateLabel extends Label implements HasConfig {

    private JavaScriptResourceReference TIMEAGO_JS = new JavaScriptResourceReference(Resource.class,"timeago/timeago-1.4.1.js");
    private JavaScriptResourceReference JS = new JavaScriptResourceReference(DateLabel.class,"datelabel.js");
    private String INIT_JS = "easy.datelabel().init(%s);"; //TODO : turn this into jquery widget?
    private transient Gson gson = new Gson();

    private Config config;
    public DateLabel(String id, DateLabelConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(TIMEAGO_JS));
        response.render(JavaScriptReferenceHeaderItem.forReference(JS));
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_JS,gson.toJson(getOptions()))));
    }

    protected DateLabelOptions getOptions() {
        return new DateLabelOptions();
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public class DateLabelOptions {
        String id = DateLabel.this.getMarkupId();
        String isoDate = getIsoDate();
    }

    protected String getIsoDate() {
        // use JODA instead of this shit....
        Date date = (Date) getDefaultModelObject();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }
}
