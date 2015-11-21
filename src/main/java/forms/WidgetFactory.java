package forms;

import com.google.common.collect.Maps;
import forms.config.Config;
import forms.config.WidgetConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidator;

import java.io.Serializable;
import java.util.Map;

public abstract class WidgetFactory implements Serializable {

    private Map<String, String> pluginNames = Maps.newHashMap();

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
        if (component instanceof FormComponent) {
            FormComponent fc = (FormComponent) component;
            addValidators(fc, config);
            addAjax(fc, config);
            setLabel(fc, config);
        }
    }

    protected void setLabel(FormComponent component, Config config) {
            component.setLabel(Model.of(config.getName()));
    }

    protected void setMetaData(Component component, Config config) {
        component.setMetaData(Config.KEY, config);
        component.setOutputMarkupId(true);
    }

    private void addValidators(FormComponent fc, Config config) {
        if (config instanceof WidgetConfig) {
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

    private void addAjax(FormComponent component, Config config) {
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
