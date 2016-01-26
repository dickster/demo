package forms;

import forms.impl.CommercialWorkflow;
import forms.impl.DemoWorkflow;
import forms.impl.PizzaWorkflow;
import forms.impl.TestWorkflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public class WfFactory implements Serializable {

    // important : note that workflow beans are PROTOTYPE scoped, so this bean must be prototype too.
    // (or ask bean factory directly programmatically instead of relying on injection).

    private @Inject CommercialWorkflow commercialWorkflow;
    private @Inject PizzaWorkflow pizzaWorkflow;
    private @Inject TestWorkflow testWorkflow;
    private @Inject
    DemoWorkflow demoWorkflow;

    public WfFactory() {
    }

    public final FormBasedWorkflow create(String workflowType) {
        FormBasedWorkflow workflow = createImpl(workflowType);
        return workflow;
    }

    public FormBasedWorkflow createImpl(@Nonnull String workflowType) {
        if ("pizza".equals(workflowType)) {
            return pizzaWorkflow;
        } else if ("test".equals(workflowType)) {
            return testWorkflow;
        } else if ("commercial".equals(workflowType)){
            return commercialWorkflow;
        }
        else if ("demo".equals(workflowType)) {
            return demoWorkflow;
        }
        throw new IllegalArgumentException("workflow type not supported : " + workflowType);
    }

}
