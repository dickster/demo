package forms;

import com.google.common.eventbus.Subscribe;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class WfPage extends WebPage {

    private DynamicForm form;

    public WfPage(PageParameters parameters) {
        super(parameters);
        // do this at application level.
        WfUtils.getFormBasedWorkflow().register(this);
        addOrReplaceForm();
    }

    private void addOrReplaceForm() {
        FormBasedWorkflow workflow = WfUtils.getFormBasedWorkflow();
        FormConfig formConfig = workflow.getCurrentFormConfig();
        addOrReplace(form = new DynamicForm("form", formConfig));
    }

    public void startProgress(WfState state) {
        //TODO : whatever you want...show spinner, dialog, etc...
    }

    public void endProgress(WfState state) {
        //TODO : whatever you want...show spinner, dialog, etc...
    }
}
