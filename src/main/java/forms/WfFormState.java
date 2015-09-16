package forms;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WfFormState extends WfState<FormBasedWorkflowContext> {

    @Nullable
    @Override
    public String enter(FormBasedWorkflowContext context, WfEvent event) {
        context.getContainer().replace(getFormForEvent(event));
        return null;
    }

    private @Nonnull Component getFormForEvent(WfEvent event) {
        return new TextField("foo"); //find form event.getName();
    }
}


// can forms be spring beans?  sure!
// form factory.

// BA - creates workflow with initial form.
// write the test app.  new DefaultWorkflow(startingFormName);   == new WfFormState(x);   formDao.getFormConfig(x);

// assume a DAO to get forms, states, workflows?
//