package forms;

import forms.config.Config;
import forms.impl.CommercialWorkflow;
import forms.impl.PizzaWorkflow;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;

import javax.inject.Inject;
import java.io.Serializable;

public class WfFactory implements Serializable {

    // important : note that workflow beans are PROTOTYPE scoped, so this bean must be prototype too.
    // (or ask bean factory directly programmatically instead of relying on injection).

    private @Inject CommercialWorkflow commercialWorkflow;
    private @Inject PizzaWorkflow pizzaWorkflow;

    public boolean customWidgets;

    public WfFactory() {

    }

    public final FormBasedWorkflow create(String workflowType) {
        FormBasedWorkflow workflow = createImpl(workflowType);
        workflow.withWidgetFactory(createWidgetFactory());
        workflow.initialize();
        return workflow;
    }

    public FormBasedWorkflow createImpl(String workflowType) {
        if ("pizza".equals(workflowType)) {
            return pizzaWorkflow;
        } else {
            return commercialWorkflow;
        }
    }


    // just for debugging purposes only to show custom widget factories.
    public WidgetFactory createWidgetFactory() {
        // example for debugging only.
        if (!customWidgets) {
            return new DefaultWidgetFactory();
        }

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



}
