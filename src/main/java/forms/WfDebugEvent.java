package forms;

import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class WfDebugEvent extends WfEvent<String> {

    private final Component component;
    private final AjaxRequestTarget target;

    public WfDebugEvent(AjaxRequestTarget target, Component component) {
        super(new WfUtil().getComponentId(component));
        this.target = target;
        this.component = component;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public Component getComponent() {
        return component;
    }
}
