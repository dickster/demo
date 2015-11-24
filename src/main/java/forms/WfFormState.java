package forms;

import com.google.common.collect.Lists;
import forms.config.FormConfig;
import forms.util.WfAjaxEventPropagation;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class WfFormState extends WfState {

    private FormConfig formConfig;
    private List<WfAjaxHandler> handlers = Lists.newArrayList();

    public WfFormState(@Nonnull FormConfig formConfig) {
        this.formConfig = formConfig;
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

    public void handleAjaxEvent(WfAjaxEvent event) {
        for (WfAjaxHandler handler:handlers) {
            if (WfAjaxEventPropagation.STOP.equals(handler.handleAjax(event))) {
                return;
            }
        }
    }

    private WorkflowForm getWorkflowForm(WfAjaxEvent event) {
        return event.getComponent().visitParents(MarkupContainer.class, new IVisitor<MarkupContainer, WorkflowForm>() {
            @Override public void component(MarkupContainer object, IVisit<WorkflowForm> visit) {
                if (object instanceof WorkflowForm) {
                    visit.stop((WorkflowForm)object);
                }
            }
        });
    }

    public WfFormState withAjaxHandlers(WfAjaxHandler... handlers) {
        this.handlers.addAll(Lists.newArrayList(handlers));
        return this;
    }



}

