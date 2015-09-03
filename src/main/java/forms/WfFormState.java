package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WfFormState extends WfState {

    @Nullable
    @Override
    public String enter(IWorkflowContext context, WfEvent event) {
        Preconditions.checkArgument(context instanceof FormBasedWorkflowContext);
        FormBasedWorkflowContext c = (FormBasedWorkflowContext) context;
        c.getContainer().replace(getFormForEvent(event));
        return null;
    }

    private @Nonnull Component getFormForEvent(WfEvent event) {
        return new TextField("foo"); //find form event.getName();
    }
}


// can forms be spring beans?  sure!
// form factory.