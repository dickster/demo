package forms.behavior;

import com.google.common.collect.Lists;
import forms.WorkflowForm;
import forms.spring.WfAjaxBehavior;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import java.util.List;

public class EmailAjaxBehavior extends WfAjaxBehavior {

    public EmailAjaxBehavior() {
        super();
    }

    public EmailAjaxBehavior(String event) {
        super(event);
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        WorkflowForm workflowForm = getWorkflowForm();
        boolean visible = StringUtils.isNotBlank(getComponent().getDefaultModelObjectAsString());
        Component notifyMe = workflowForm.getWfComponent("insured.notifyMe");
        notifyMe.setVisible(visible);
        target.add(notifyMe);
    }

}
