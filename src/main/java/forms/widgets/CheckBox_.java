package forms.widgets;

import forms.config.CheckBoxConfig;
import forms.config.Config;
import forms.config.HasConfig;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class CheckBox_ extends Panel implements HasConfig {
    private final CheckBoxConfig config;

    public CheckBox_(String id, CheckBoxConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("label", config.getLabel()));
        add(new CheckBox("checkbox", (IModel<Boolean>) getDefaultModel()));
    }

//
//    @Override
//    protected void onComponentTag(ComponentTag tag) {
//        tag.setName("input");
//        tag.getAttributes().put("type", "checkbox");
//        super.onComponentTag(tag);
//    }

    @Override
    public Config getConfig() {
        return config;
    }
}
