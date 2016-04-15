package forms.widgets;

import forms.model.ParentModel;
import forms.widgets.config.CheckBoxConfig;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponentPanel;

public class WfCheckBox extends FormComponentPanel<Boolean> implements HasConfig {
    private final CheckBoxConfig config;
    private CheckBox checkbox;

    // TODO : CONFIRM that i can add ajax behaviors to panels.
    // otherwise i will need a "getFormComponent" method to find underlying Comp.

    public WfCheckBox(String id, CheckBoxConfig config) {
        super(id);
        add(checkbox = new CheckBox("checkbox"));
        add(new Label("label", config.getLabel()));
        this.config = config;
    }

    @Override
    protected void convertInput() {
        setConvertedInput(checkbox.getConvertedInput());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        checkbox.setModel(new ParentModel(this));
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
