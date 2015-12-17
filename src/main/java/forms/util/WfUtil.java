package forms.util;

import forms.WfPage;
import forms.Workflow;
import forms.WorkflowForm;
import forms.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

public class WfUtil implements Serializable {

    // TODO: can i just make this a static method?  or will that
    // screw up any attempts to test?? hmmm....
    public @Nullable String getComponentName(@Nonnull Component component) {
        if (component instanceof HasConfig) {
            return ((HasConfig)component).getConfig().getId();
        }
        return null;
    }

    public String getComponentProperty(@Nonnull Component component) {
        if (component instanceof HasConfig) {
            return ((HasConfig)component).getConfig().getProperty();
        }
        throw new IllegalArgumentException("component doesn't have a property");
    }

    public Workflow getWorkflowFor(Component component) {
        Workflow workflow = component.visitParents(WfPage.class, new IVisitor<WfPage, Workflow>() {
            @Override
            public void component(WfPage wf, IVisit visit) {
                visit.stop(wf.getWorkflow());
            }
        });
        if (workflow==null) {
            throw new IllegalStateException("can't find workflow for component " + component.getId() + getComponentName(component));
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
