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
    // TODO : don't use list.  rather, just inject listeners? will solve serializable thing?
    // each AjaxHandler should have getId/Id(),  getEvent();
    // forms should validate that components with ajax turned on have listeners &
    // listeners for events have components that trigger.
    // ****that is redundant, just need a "add ajax" phase after form is built.
    // done by widgetFactory.ajaxify(form);  need access to state & workflow.
    // (workflow specific) event handlers need to be passed form to help decide?
    //  nah, then they should just make it form specific.
    private List<WfAjaxHandler> handlers = Lists.newArrayList();

    public WfFormState(@Nonnull FormConfig formConfig) {
        super();
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

