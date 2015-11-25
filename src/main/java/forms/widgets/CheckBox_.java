package forms.widgets;

import forms.config.CheckBoxConfig;
import forms.config.Config;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.CheckBox;

import javax.inject.Inject;

public class CheckBox_ extends CheckBox implements HasConfig {
    private final CheckBoxConfig config;

    private @Inject WfUtil wfUtil;

    public CheckBox_(String id, CheckBoxConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        wfUtil.render(this, response);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "checkbox");
        super.onComponentTag(tag);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
