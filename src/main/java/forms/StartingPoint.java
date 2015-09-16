package forms;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

import javax.inject.Inject;

public class StartingPoint {

    private @Inject WfFactory workflowFactory;

    public void foo() {
        new AjaxLink("newBusiness") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                workflowFactory.create("comml", new FormBasedWorkflowContext(this).withAjaxEnabled());
            }
        };
    }

}