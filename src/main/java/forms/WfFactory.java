package forms;

import forms.config.Config;
import forms.config.GroupConfig;
import forms.config.TextFieldConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.validation.validator.StringValidator;

public class WfFactory {

    public boolean customTheme = true;
    public boolean customWidgets;

    public FormBasedWorkflow create(String workflowType) {
        CommercialWorkflow workflow = new CommercialWorkflow();
        workflow.withWidgetFactory(createWidgetFactory());
        return workflow;
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


}
