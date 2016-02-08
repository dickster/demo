package forms.widgets;

import forms.spring.LabelFormatter;
import forms.spring.LabelFormatterFactory;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.LabelConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import javax.inject.Named;

public class Label2 extends Label implements HasConfig {

    private @Inject LabelFormatterFactory formatterFactory;
    private @Inject @Named("defaultLabelFormatter") LabelFormatter defaultFormatter;

    private final LabelConfig config;

    public Label2(String id, LabelConfig config) {
        super(id);
        this.config = config;
        setEscapeModelStrings(!config.isHtmlStrings());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        String text = getFormatter().format(this, config);
        setDefaultModel(Model.of(text));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        // TODO : should make this a config option.
        tag.setName(config.getTagName());
        super.onComponentTag(tag);
    }

    public LabelFormatter getFormatter() {
        String formatterName = config.getFormatter();
        if (StringUtils.isNotBlank(formatterName)) {
            return formatterFactory.create(formatterName);
        }
        return defaultFormatter;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
