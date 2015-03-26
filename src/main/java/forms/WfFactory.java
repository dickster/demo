package forms;

import com.google.common.base.Preconditions;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class WfFactory { //implements ApplicationContextAware {

  //  private ApplicationContext applicationContext;

//    public Workflow createWorkflow(String wfName) {

//    EventBus eventBus = new EventBus();
//    PurchaseSubscriber purchaseSubscriber = new PurchaseSubscriber();
//    eventBus.register(purchaseSubscriber);



//        Preconditions.checkNotNull(wfName);
//        Map<String, Workflow> workflows = applicationContext.getBeansOfType(Workflow.class);
//        for (Workflow workflow: workflows.values()) {
//            if (wfName.equals(getName(workflow))) {
//                return workflow;
//            }
//        }
//        throw new IllegalStateException("can't find workfow " + wfName
//                    + " possible workflows are..." + getWorkflowsAsString(workflows));
//    }

    private Workflow find(String wfName) {
        throw new IllegalArgumentException("can't find workflow " + wfName);
    }

    private String getWorkflowsAsString(Map<String, Workflow> workflows) {
        StringBuilder s = new StringBuilder("{");
        for (String type:workflows.keySet()) {
            s.append(type);
            s.append("-->");
            s.append(workflows.get(type));
            s.append(",");
        }
        s.append("}");
        return s.toString();
    }

    private String getName(Workflow workflow) {
        WfDef def = workflow.getClass().getAnnotation(WfDef.class);
        if (def!=null) {
            return def.name();
        }
        throw new IllegalArgumentException("workflow " + workflow.getClass().getSimpleName() + " has no @" + WfDef.class.getSimpleName() + " definition");
    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }

}