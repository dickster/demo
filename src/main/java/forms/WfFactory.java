package forms;

import forms.config.Config;
import forms.config.GroupConfig;
import forms.config.TextFieldConfig;
import forms.impl.CommercialWorkflow;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.validation.validator.StringValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;

public class WfFactory implements Serializable, ApplicationContextAware {

    // important : note that workflow beans are PROTOTYPE scoped.

    private @Inject CommercialWorkflow commercialWorkflow;

    public boolean customTheme = true; // DEBUG ONLY!!
    public boolean customWidgets;
    private ApplicationContext applicationContext;

    public WfFactory() {
        System.out.println("create wffactory");
    }

    @PostConstruct
    public void foo() {
        System.out.println(applicationContext);
    }

    public final FormBasedWorkflow create(String workflowType) {
        FormBasedWorkflow workflow = createImpl(workflowType);
        workflow.withWidgetFactory(createWidgetFactory());
        workflow.initialize();
        return workflow;
    }

    public FormBasedWorkflow createImpl(String workflowType) {
        return commercialWorkflow;
    }


    // just for debugging purposes only to show custom widget factories.
    public WidgetFactory createWidgetFactory(GroupConfig formConfig) {
        // example for debugging only.
        if (!customWidgets) {
            return createWidgetFactory();
        }

        if ("FORM-A".equals(formConfig.getName())) {
            return new DefaultWidgetFactory() {
                @Override
                public Component create(String id, Config config) {
                    if ("name.first".equals(config.getName())) {
                        return new Label(id, config.getName() + " (custom widget)");
                    }
                    return super.create(id, config);
                }
            };
        }
        else if ("FORM-A-relayout".equals(formConfig.getName())) {
            return new DefaultWidgetFactory() {
                @Override
                public Component create(String id, Config config) {
                    if (config instanceof TextFieldConfig) {
                        ((TextFieldConfig)config).required().addValidator(new StringValidator(1,5));
                        return super.create(id, config);
                    }
                    return super.create(id, config);
                }
            };
        }
        else {
            return createWidgetFactory();
        }
    }

    public WidgetFactory createWidgetFactory() {
        //depending on sessionUser, create appropriate widgetFactory.
        // may need to pass form,workflow, state info as well?
        return new DefaultWidgetFactory();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
