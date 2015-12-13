package forms;

import com.google.common.collect.Maps;
import forms.config.Config;
import forms.config.FormComponentConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidator;

import java.io.Serializable;
import java.util.List;
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
        // TODO : register all widgets so they can @Post & @Subscribe.

        if (config instanceof FormComponentConfig && component instanceof FormComponent) {
            FormComponent fc = (FormComponent) component;
            FormComponentConfig fcc = (FormComponentConfig) config;
            addValidators(fc, fcc);
            addAjax(fc, fcc);
            setLabel(fc, fcc);
        }
        component.add(new RenderingBehaviour());
    }

    protected void setLabel(FormComponent component, FormComponentConfig config) {
            component.setLabel(Model.of(config.getId()));
    }

    protected void setMetaData(Component component, Config config) {
        component.setOutputMarkupId(true);
    }

    private void addValidators(FormComponent fc, FormComponentConfig<?> config) {
        // yuck.   fix this shit!  can you add validators to a panel?

        for (IValidator<?> validator:config.getValidators()) {
            fc.add(validator);
        }
        if (config.isRequired()) {
            fc.setRequired(true);
        }
    }

    private void addAjax(FormComponent component, FormComponentConfig<?> config) {
        List mediatedAjaxEvents = config.getMediatedAjaxEvents();
        for (String event:config.getMediatedAjaxEvents()) {
            component.add(new MediatedAjaxEventBehavior(event));
        }
    }

    protected void preCreate(Config config) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getId().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

}
