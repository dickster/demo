package forms;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class StartingPoint extends WebPage {

    public StartingPoint() {
        super(new PageParameters());
        add(new Form("form")
                .add( new AjaxSubmitLink("start") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                        setResponsePage(new WfPage(new CommercialWorkflow()));
                    }
                })
        );
    }

}
