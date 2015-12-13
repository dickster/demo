package forms;

import forms.impl.CommercialWorkflow;
import forms.impl.PizzaWorkflow;

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

}
