package forms;

import forms.impl.*;

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
    TemplateWorkflow templateWorkflow;
    private @Inject
    DemoWorkflow demoWorkflow;

    public WfFactory() {
    }

    public final Workflow create(String workflowType) {
        Workflow workflow = createImpl(workflowType);
        return workflow;
    }

    public Workflow createImpl(@Nonnull String workflowType) {
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
        else if ("template".equals(workflowType)) {
            return templateWorkflow;
        }
        throw new IllegalArgumentException("workflow type not supported : " + workflowType);
    }

}
