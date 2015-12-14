package forms;

import forms.impl.CommercialWorkflow;
import forms.impl.PizzaWorkflow;
import forms.impl.TestWorkflow;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public class WfFactory implements Serializable {

    // important : note that workflow beans are PROTOTYPE scoped, so this bean must be prototype too.
    // (or ask bean factory directly programmatically instead of relying on injection).

    private @Inject CommercialWorkflow commercialWorkflow;
    private @Inject PizzaWorkflow pizzaWorkflow;
    private @Inject TestWorkflow testWorkflow;

    public WfFactory() {
    }

    public final FormBasedWorkflow create(String workflowType, WfCompoundPropertyModel model) {
        FormBasedWorkflow workflow = createImpl(workflowType);
        workflow.withModel(model).initialize();
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
        throw new IllegalArgumentException("workflow type not supported : " + workflowType);
    }

}
