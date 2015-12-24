package forms.widgets;

import forms.spring.LabelFormatter;
import forms.spring.LabelFormatterFactory;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.LabelConfig;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

public class Label2 extends Label implements HasConfig {

    private @Inject LabelFormatterFactory formatterFactory;

    // need way to configure any kind of calculated string here.
    // formatted string with model ognls passed???
    private final LabelConfig config;

    public Label2(String id, LabelConfig config) {
        super(id);
        this.config = config;
        setEscapeModelStrings(!config.isHtmlStrings());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        LabelFormatter formatter = formatterFactory.create(config.getFormatter());
        String text = formatter.format(this, config);//
        setDefaultModel(Model.of(text));
    }

    private Object getFormatValues() {
        // TODO : if messageFormat, allow returning of multiple properties.
        return getDefaultModelObject();
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
