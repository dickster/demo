package forms.widgets;

import forms.config.Config;
import forms.config.HasConfig;
import forms.config.TextFieldConfig;
import forms.util.WfUtil;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;

import javax.inject.Inject;

public class TextField2 extends TextField implements HasConfig {

    // TODO : for all widgets.  i should include the config in the class.
    // api = TextField2.config(blah, blah);   (reduces scattered classes).
    // should config and widgets be in same package?

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
        wfUtil.render(this, response);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }


}
