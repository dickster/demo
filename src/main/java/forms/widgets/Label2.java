package forms.widgets;

import forms.config.Config;
import forms.config.HasConfig;
import forms.config.LabelConfig;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import java.text.Format;

public class Label2 extends Label implements HasConfig {

    // need way to configure any kind of calculated string here.
    // formatted string with model ognls passed???
    private final LabelConfig config;

    public Label2(String id, LabelConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        if (config.getText()!=null) {
            setDefaultModel(Model.of(config.getText()));
        }
        else {
            Format format = config.getFormat();
            String formattedValue = format.format(getDefaultModelObject());
            setDefaultModel(Model.of(formattedValue));
        }
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
