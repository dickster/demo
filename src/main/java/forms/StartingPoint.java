package forms;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class StartingPoint extends WebPage {

    private @SpringBean WfFactory workflowFactory;

    public StartingPoint() {
        super(new PageParameters());
        add(new Form("form")
                .add( new AjaxSubmitLink("start") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                        Workflow wf = workflowFactory.create("comml");
                        setResponsePage(new WfPage((FormBasedWorkflow) wf));
                    }
                })
        );
    }

}
