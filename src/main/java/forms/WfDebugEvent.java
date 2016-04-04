package forms;

import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class WfDebugEvent extends WfEvent<String> {

    public WfDebugEvent(AjaxRequestTarget target, Component component) {
        super(component.getPath(), target, component);
    }

}
