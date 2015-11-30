package forms.widgets;

import demo.resources.Resource;
import forms.config.Config;
import forms.config.HasConfig;
import forms.config.TextFieldConfig;
import forms.util.WfUtil;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import javax.inject.Inject;

public class TextField2 extends TextField implements HasConfig {

    // TODO : for all widgets.  i should include the config in the class.
    // api = TextField2.config(blah, blah);   (reduces scattered classes).
    // should config and widgets be in same package?

    private static final JavaScriptResourceReference INPUTGROUP_JS = new JavaScriptResourceReference(Resource.class, "inputgroup.js");

    private @Inject WfUtil wfUtil;

    private Config config;

    public TextField2(String id, TextFieldConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "text");
        super.onComponentTag(tag);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(INPUTGROUP_JS));
        wfUtil.render(this, response);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }


}
