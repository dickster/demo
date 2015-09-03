package forms;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;


public class WfFactory implements ApplicationContextAware {

    // spring based WfFactory....
    // WfFactory interface.
    // defaultWfFactory creates unmanagedFormStates.  has reference to parent MarkupContainer.
    // init method gets parent container. every time you show a new form, you replace the "form-panel widget.

    private ApplicationContext context;

    public Workflow create(String workflowType, Map<String, Object> context) {

        // default = new DefaultWorkflow().withValues(context).withContainer(
        return new CommercialWorkflow().withValues(context);
//        boolean isPrototype = context.isPrototype(workflowType);
//        Preconditions.checkState(isPrototype, "workflow beans must be of PROTOTYPE scope.   see @org.springframework.beans.factory.config.Scope");
//        return context.getBean(workflowType, Workflow.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = context;
    }
}

// easy for BA.  automatically created.
// dev can add states easily
// states can override handling of events.
// (groovy states?)
// events can have parameters.
// states can be based on forms. (require container which should be in workflow itself)
//