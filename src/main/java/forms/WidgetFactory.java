package forms;

import forms.config.Config;
import forms.config.FormComponentConfig;
import forms.spring.AjaxBehaviorFactory;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public abstract class WidgetFactory implements Serializable {

    public static final MetaDataKey<String> MODEL_PREFIX = new MetaDataKey<String>(){};

    private @Inject AjaxBehaviorFactory ajaxBehaviorFactory;

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    @Nonnull
    public abstract Component create(String id, Config config);

    /*package protected*/ Component createWidget(String id, Config config, String... prefix) {
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
            setLabel(fc, fcc);
        }
        addAjaxBehaviors(component, config);
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
        for (IValidator<?> validator:config.getValidators()) {
            fc.add(validator);
        }
        if (config.isRequired()) {
            fc.setRequired(true);
        }
    }

    private final void addAjaxBehaviors(Component component, Config<?> config) {
        for (String handlerName:config.getAjaxBehaviors()) {
            component.add(ajaxBehaviorFactory.create(handlerName));
        }
    }

    protected void preCreate(Config config) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getId().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

}
