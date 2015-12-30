package forms.util;

import com.google.common.base.Joiner;
import forms.WfEvent;
import forms.WfPage;
import forms.WidgetFactory;
import forms.Workflow;
import forms.WorkflowForm;
import forms.widgets.config.HasConfig;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

public class WfUtil implements Serializable {

    public static @Nullable String getComponentId(@Nonnull Component component) {
        if (component instanceof HasConfig) {
            return ((HasConfig)component).getConfig().getId();
        }
        return null;
    }

    public static String getComponentFullProperty(@Nonnull Component component) {
        if (component instanceof HasConfig) {
            // check meta data...
            String prefix = component.getMetaData(WidgetFactory.MODEL_PREFIX);
            String property = ((HasConfig) component).getConfig().getProperty();
            return Joiner.on('.').skipNulls().join(prefix, property);
        }
        throw new IllegalArgumentException("component doesn't have a property");
    }

    // blargh : i need a better way to access workflow.
    // in session?  nah, multiples allowed. attached to page?  reference injected into each component?
    // can it be a spring bean?
    public static Workflow getWorkflow(Component component) {
        Workflow workflow = component.visitParents(WfPage.class, new IVisitor<WfPage, Workflow>() {
            @Override
            public void component(WfPage wf, IVisit visit) {
                visit.stop(wf.getWorkflow());
            }
        });
        if (workflow==null) {
            throw new IllegalStateException("can't find workflow for component " + component.getId() + getComponentId(component));
        }
        return workflow;
    }

    public static WorkflowForm getWorkflowForm(@Nonnull Component component) {
        return component.visitParents(MarkupContainer.class, new IVisitor<MarkupContainer, WorkflowForm>() {
            @Override public void component(MarkupContainer container, IVisit<WorkflowForm> visit) {
                if (container instanceof WorkflowForm) {
                    visit.stop((WorkflowForm)container);
                }
            }
        });
    }

    public static boolean isDebug() {
        Application application = Application.get();
        if (application instanceof WebApplication) {
            switch (((WebApplication)application).getConfigurationType()) {
                case DEVELOPMENT:
                    return true;
                case DEPLOYMENT:
                    return false;
            }
        }
        return false;
    }

    public static void post(Component component, WfEvent event) {
        getWorkflow(component).post(event);
    }
}
