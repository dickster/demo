package forms;

import forms.config.WidgetConfig;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.validation.IValidator;

import java.io.Serializable;

public abstract class WidgetFactory implements Serializable {

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    public abstract Component create(String id, WidgetConfig config);

    public Component createWidget(String id, WidgetConfig config) {
        preCreate(config);
        Component component = create(id, config);
        postCreate(component, config);
        return component;
    }

    protected void postCreate(final Component component, WidgetConfig config) {
        setMetaData(component, config);
        addAjax(component, config);
        addValidators(component, config);
    }

    protected void setMetaData(Component component, WidgetConfig config) {
        String name = config.getName()==null ? config.getProperty() : config.getName();
        WfUtil.setComponentName(component, name);
        component.setOutputMarkupId(true);
    }

    private void addValidators(Component component, WidgetConfig config) {
        if (component instanceof FormComponent) {
            FormComponent fc = (FormComponent) component;
            for (IValidator<?> validator:config.getValidators()) {
                fc.add(validator);
            }
        }
    }

    private void addAjax(Component component, WidgetConfig config) {
        //        Preconditions.checkArgument(component instanceof FormComponent);
        // if this is panel, how to add behaviour to underlying text field?
//        final String event = "onchange";//TODO: config.getMediatedEvent();
//        if (StringUtils.isNotBlank(event)) {
//            if (StringUtils.isNotBlank(event)) {
//                component.addMediatedBehavior(new MediatedAjaxEventBehavior(event));
//            }
//        }
    }

    protected void preCreate(WidgetConfig config) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getName().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

}
