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
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class WfCheckBox extends FormComponentPanel<Boolean> implements HasConfig {
    private final CheckBoxConfig config;
    private CheckBox checkbox;

// TODO CONFIRM that i can add ajax behaviors to panels.
// otherwise i will need a "getFormComponent" method to find underlying Comp.
private Boolean foo;

    public WfCheckBox(String id, CheckBoxConfig config) {
        super(id);
        add(checkbox = new CheckBox("checkbox"));
        add(new Label("label", config.getLabel()));
        // need to set model here...??
        this.config = config;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }


    @Override
    protected void convertInput() {
        setConvertedInput(checkbox.getConvertedInput());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        checkbox.setModel(getParentModel());
    }


    private IModel<Boolean> getParentModel() {
        final IModel<Boolean> model = getModel();
        return new IModel<Boolean>() {
            @Override public Boolean getObject() {
                    return getModel().getObject();
            }

            @Override public void setObject(Boolean b) {
                getModel().setObject(b);
            }

            @Override public void detach() {
                getModel().detach();
            }
        };
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
