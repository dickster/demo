package forms.util;

import forms.WfPage;
import forms.WidgetFactory;
import forms.Workflow;
import forms.WorkflowForm;
import forms.config.Config;
import forms.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.Serializable;

public class WfUtil implements Serializable {

    private static final String INIT_WIDGET_JS = "workflow.initWidget(%s);";
    private @Inject ConfigGson gson;

    public @Nullable String getComponentName(@Nonnull Component component) {
        if (component instanceof HasConfig) {
            return ((HasConfig)component).getConfig().getName();
        }
        return null;
    }

    public String getComponentProperty(@Nonnull Component component) {
        if (component instanceof HasConfig) {
            return ((HasConfig)component).getConfig().getProperty();
        }
        throw new IllegalArgumentException("component doesn't have a property");
    }

    public WidgetFactory getWidgetFactoryFor(Component component) {

        Workflow workflow = component.visitParents(WfPage.class, new IVisitor<WfPage, Workflow>() {
            @Override
            public void component(WfPage wf, IVisit visit) {
                visit.stop(wf.getWorkflow());
            }
        });
        if (workflow!=null) {
            return workflow.getWidgetFactory();
        }
        throw new IllegalStateException("can't find workflow required to get widget factory for component " + component.getId() + getComponentName(component));

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


    // TODO : refactor this out into spring bean = component renderer.
    public <T extends Component & HasConfig> void render(T widget, IHeaderResponse response) {
        String js = String.format(INIT_WIDGET_JS, gson.toJson(new WidgetData(widget)));
        response.render(OnDomReadyHeaderItem.forScript(js));

    }

    class WidgetData {
        String markupId;
        Config config;

        public <T extends Component & HasConfig> WidgetData(T widget) {
            this.markupId = widget.getMarkupId();
            this.config = widget.getConfig();
        }
    }

}
