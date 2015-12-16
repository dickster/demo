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
    // TODO : should use an iconConfig object for this
    // or just do it in javascript.
    private String iconFormat = "<i class='%s'></i>";

    public Label2(String id, LabelConfig config) {
        super(id);
        this.config = config;
        setEscapeModelStrings(!config.isHtmlStrings());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        String text = null;
        if (config.getText()!=null) {
            text = config.getText();
        }
        else {
            Format format = config.getFormat();
            String formattedValue;
            if (format!=null) {
                text = format.format(getDefaultModelObject());
            }
            else {
                text = getDefaultModelObjectAsString();
            }
        }
        if (config.getIcon()!=null) {
            text = String.format(iconFormat, config.getIcon()) + text;
        }
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
