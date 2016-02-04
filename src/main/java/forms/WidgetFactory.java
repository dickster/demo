package forms;

import forms.spring.BehaviorFactory;
import forms.widgets.config.Config;
import forms.widgets.config.FormComponentConfig;
import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public abstract class WidgetFactory implements Serializable {

    // note : this isn't serialized so might break when page is de-serialized.
    // find a better way to do this...check out SectionPanel???
    // set the parent component to have a model with prefix and work from there???
    public static final MetaDataKey<String> MODEL_PREFIX = new MetaDataKey<String>(){};
    // ----------------------

    private @Inject BehaviorFactory behaviorFactory;

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    @Nonnull
    public abstract <T extends Component & HasConfig> T create(String id, Config config);

    /*package protected*/ <T extends Component & HasConfig> T createWidget(String id, Config config, String... prefix) {
        preCreate(config);
        T component = create(id, config);
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
        addBehaviors(component, config);
        addRenderingBehavior(component);
    }

    protected void setVisibility(Component component, Config config) {
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
        component.setOutputMarkupPlaceholderTag(true);
    }

    private void addValidators(FormComponent fc, FormComponentConfig<?> config) {
        for (IValidator<?> validator:config.getValidators()) {
            fc.add(validator);
        }
        if (config.isRequired()) {
            fc.setRequired(true);
        }
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
