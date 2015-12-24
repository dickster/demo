package forms;

import com.google.common.collect.Lists;
import forms.spring.WfAjaxBehavior;
import forms.widgets.config.FormConfig;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class WfFormState extends WfState {

    private FormConfig formConfig;
    private List<WfAjaxBehavior> handlers = Lists.newArrayList();

    public WfFormState(@Nonnull FormConfig formConfig) {
        super();
        this.formConfig = formConfig;
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

    @Deprecated
    private WorkflowForm getWorkflowForm(WfAjaxEvent event) {
        return event.getComponent().visitParents(MarkupContainer.class, new IVisitor<MarkupContainer, WorkflowForm>() {
            @Override public void component(MarkupContainer object, IVisit<WorkflowForm> visit) {
                if (object instanceof WorkflowForm) {
                    visit.stop((WorkflowForm)object);
                }
            }
        });
    }

    public WfFormState withAjaxBehaviors(WfAjaxBehavior... behaviors) {
        this.handlers.addAll(Lists.newArrayList(behaviors));
        return this;
    }

}

