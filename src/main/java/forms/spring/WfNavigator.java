package forms.spring;

import forms.WfPage;
import forms.Workflow;
import forms.WorkflowForm;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.io.Serializable;

public class WfNavigator implements Serializable {

    // blargh : i need a better way to access workflow.
    // in session?  nah, multiples allowed. attached to page?  reference injected into each component?
    // can it be a spring bean?
    public Workflow getWorkflow(Component component) {
        Workflow workflow = component.visitParents(WfPage.class, new IVisitor<WfPage, Workflow>() {
            @Override
            public void component(WfPage wf, IVisit visit) {
                visit.stop(wf.getWorkflow());
            }
        });
        if (workflow==null) {
            throw new IllegalStateException("can't find workflow for component " + component.getId() + WfUtil.getComponentId(component));
        }
        return workflow;
    }

    public WorkflowForm getWorkflowForm(@Nonnull Component component) {
        return component.visitParents(MarkupContainer.class, new IVisitor<MarkupContainer, WorkflowForm>() {
            @Override public void component(MarkupContainer container, IVisit<WorkflowForm> visit) {
                if (container instanceof WorkflowForm) {
                    visit.stop((WorkflowForm)container);
                }
            }
        });
    }

}
