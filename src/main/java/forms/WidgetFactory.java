package forms;

import forms.spring.DynamicInjector;
import forms.widgets.config.Config;
import forms.widgets.config.FormComponentConfig;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public abstract class WidgetFactory implements Serializable {

    // note : this isn't serialized so might break when page is de-serialized.
    // find a better way to do this...check out SectionPanel???
    // set the parent component to have a model with prefix and work from there???
    public static final MetaDataKey<String> MODEL_PREFIX = new MetaDataKey<String>(){};


    // ----------------------

    private @Inject DynamicInjector injector;

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    @Nonnull
    public abstract Component create(String id, Config config);


    public Component createWidget(String id, Config config, String... prefix) {
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
        setVisibility(component, config);
        injector.inject(component, config);
        addBehaviors(component, config);
        addRenderingBehavior(component);
    }

    protected void setVisibility(Component component, Config config) {
        component.setVisible(!config.isInitialyHidden());
    }

    protected void addRenderingBehavior(Component c) {
        // need to make rendering behaviour configurable/extendable.
        // make this a bean... .: i need a factory for this.
        // put this in behaviorFactory
        c.add(behaviorFactory.create("renderingBehavior"));
    }

    protected void setLabel(FormComponent component, FormComponentConfig config) {
        component.setLabel(Model.of(config.getId()));
    }

    protected void setMetaData(Component component, Config config) {
        if (component.getOutputMarkupPlaceholderTag()) {
            System.out.println("WARNING: all workflow widgets have custom placeholder renderers attached. Setting 'outputPlaceholderTag' for component " + component.getId() + " is being ignored/overridden!");
        }
        component.setOutputMarkupPlaceholderTag(false);
    }

    // should these be spring beans?
    private void addValidators(FormComponent fc, FormComponentConfig<?> config) {
        for (String validator:config.getValidators()) {
            fc.add(resolveValidator(validator));
        }
        if (config.isRequired()) {
            fc.setRequired(true);
        }
    }

    private IValidator resolveValidator(String validator) {
        if ("email".equalsIgnoreCase(validator)) {
            return EmailAddressValidator.getInstance();
        }
        // TODO : check spring bean @config for validator with given name.
        // hard coded for now.
        throw new UnsupportedOperationException("can't resolve validator " + validator);
    }

    private final void addBehaviors(Component component, Config<?> config) {
        for (String behaviorName:config.getBehaviors()) {
            component.add(behaviorFactory.create(behaviorName));
        }
    }

    protected void preCreate(Config config) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getId().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

}
