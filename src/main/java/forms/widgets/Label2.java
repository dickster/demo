package forms.widgets;

import forms.config.Config;
import forms.config.HasConfig;
import forms.config.LabelConfig;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class Label2 extends Label implements HasConfig {

    private final LabelConfig config;

    public Label2(String id, LabelConfig config) {
        super(id);
        if (config.getText()!=null) {
            setDefaultModel(Model.of(config.getText()));
        }  // otherwise, assume it's a property model.
        this.config = config;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
