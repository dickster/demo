package forms;

import forms.config.WidgetConfig;
import forms.config.Config;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidator;

import java.io.Serializable;

public abstract class WidgetFactory implements Serializable {

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    public abstract Component create(String id, Config config);

    public Component createWidget(String id, Config config) {
        preCreate(config);
        Component component = create(id, config);
        postCreate(component, config);
        return component;
    }

    protected void postCreate(final Component component, Config config) {
        setMetaData(component, config);
        addAjax(component, config);
        addValidators(component, config);
        setLabel(component, config);
    }

    private void setLabel(Component component, Config config) {
        if (component instanceof FormComponent) {
            ((FormComponent)component).setLabel(Model.of(config.getName()));
        }
    }

    protected void setMetaData(Component component, Config config) {
        String name = config.getName()==null ? config.getProperty() : config.getName();
        WfUtil.setComponentName(component, name);
        component.setOutputMarkupId(true);
    }

    private void addValidators(Component component, Config config) {
        if (component instanceof FormComponent && config instanceof WidgetConfig) {
            FormComponent fc = (FormComponent) component;
            WidgetConfig c = (WidgetConfig) config;
            // yuck.   fix this shit!  can you add validators to a panel?
            for (IValidator<?> validator:c.getValidators()) {
                fc.add(validator);
            }
            if (c.isRequired()) {
                fc.setRequired(true);
            }
        }
    }

    private void addAjax(Component component, Config config) {
        //        Preconditions.checkArgument(component instanceof FormComponent);
        // if this is panel, how to add behaviour to underlying text field?
//        final String event = "onchange";//TODO: config.getMediatedEvent();
//        if (StringUtils.isNotBlank(event)) {
//            if (StringUtils.isNotBlank(event)) {
//                component.addMediatedBehavior(new MediatedAjaxEventBehavior(event));
//            }
//        }
    }

    protected void preCreate(Config config) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getName().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

}
