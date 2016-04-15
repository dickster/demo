package forms.widgets;

import forms.DynamicInjection;
import forms.spring.LabelFormatter;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.LabelConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class WfLabel extends Label implements HasConfig {

    @DynamicInjection(property="labelFormatter", dflt="defaultLabelFormatter")
    private  LabelFormatter formatter;

    private final LabelConfig config;

    public WfLabel(String id, LabelConfig config) {
        super(id);
        this.config = config;
        setEscapeModelStrings(!config.isHtmlStrings());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        String text = formatter.format(this, config);
        setDefaultModel(Model.of(text));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName(config.getTagName());
        super.onComponentTag(tag);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
