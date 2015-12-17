package forms;

import forms.config.Config;
import forms.config.FormComponentConfig;
import forms.spring.AjaxHandlerFactory;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public abstract class WidgetFactory implements Serializable {

    private @Inject AjaxHandlerFactory ajaxHandlerFactory;

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    @Nonnull
    public abstract Component create(String id, Config config);

    /*package protected*/ Component createWidget(String id, Config config) {
        preCreate(config);
        Component component = create(id, config);
        postCreate(component, config);
        return component;
    }

    protected void postCreate(final Component component, Config config) {
        setMetaData(component, config);
        if (config instanceof FormComponentConfig && component instanceof FormComponent) {
            FormComponent fc = (FormComponent) component;
            FormComponentConfig fcc = (FormComponentConfig) config;
            addValidators(fc, fcc);
            addAjaxHandlers(fc, fcc);
            setLabel(fc, fcc);
        }
        addBehaviors(component);
    }

    protected void addBehaviors(Component c) {
        // need to make rendering behaviour configurable/extendable.
        c.add(new RenderingBehaviour());
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

    private final void addAjaxHandlers(FormComponent component, FormComponentConfig<?> config) {
        for (String handlerName:config.getAjaxHandlers()) {
            component.add(ajaxHandlerFactory.create(handlerName));
        }
    }

    protected void preCreate(Config config) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getId().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

}
