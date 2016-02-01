package forms.widgets;

import forms.widgets.config.CheckBoxConfig;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class WfCheckBox extends CheckBox implements HasConfig {
    private final CheckBoxConfig config;

    public WfCheckBox(String id, CheckBoxConfig config) {
        super(id);
        this.config = config;
        setOutputMarkupId(true);
        // TODO : add javascript that will render a label in bootstrap friendly form.
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "checkbox");
        super.onComponentTag(tag);
    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        super.onComponentTagBody(markupStream, openTag);
        getResponse().write(config.getLabel());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
