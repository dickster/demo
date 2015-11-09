package forms;

import forms.config.Config;
import forms.config.WidgetConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

import java.io.Serializable;

public abstract class WidgetFactory implements Serializable {

    public WidgetFactory(/**user, locale, settings, permissions - get this from session.*/) {
    }

    public abstract Component create(String id, WidgetConfig config, IModel<?> model);

//    public <T extends WidgetFactory> T usingPropertyAsName() {
//        usePropertyAsName = true;
//        return (T) this;
//    }

    public Component createWidget(String id, WidgetConfig config, IModel<?> model) {
        preCreate(config, model);
        Component component = create(id, config, model);
        postCreate(component, config, model);
        return component;
    }

    protected void postCreate(final Component component, WidgetConfig config, IModel<?> model) {
        setMetaData(component, config);
       // setModels(component, model);
        addAjax(component, config);
        addValidators(component, config);
    }

    protected void setMetaData(Component component, WidgetConfig config) {
        String name = config.getName()==null ? config.getProperty() : config.getName();
        component.setMetaData(Config.NAME, name);
        component.setOutputMarkupId(true);
    }

//    private void setModels(Component component, IModel<?> model) {
//        if (component instanceof IHasMultipleModels) {
//            IHasMultipleModels mm = (IHasMultipleModels) component;
//            mm.setDefaultModels(models);
//        } else {
//            if (models.length>1) System.out.println("WARNING : you have supplied more than one model to " + component.getMetaData(Config.NAME) + " but it doesn't implement interface " + IHasMultipleModels.class.getSimpleName());
//            component.setDefaultModel(model);
//        }
//    }

    private void addValidators(Component component, WidgetConfig config) {
        if (component instanceof FormComponent) {
            FormComponent fc = (FormComponent) component;
            for (IValidator<?> validator:config.getValidations()) {
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
//                component.add(new MediatedAjaxEventBehavior(event));
//            }
//        }
    }

    protected void preCreate(WidgetConfig config, IModel<?> formModel) {
        // do nothing by default.  you might want to filter config options based on user/settings.
        //if (config.getName().equals("someSpecialEmail")) { config.addAjaxEvent("onchange"); config.addValidator(EmailAddressValidator.getInstance()); }
    }

}
