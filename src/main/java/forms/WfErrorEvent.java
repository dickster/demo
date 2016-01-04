package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import javax.annotation.Nullable;

public class WfErrorEvent<T> extends WfEvent<T> {

    public WfErrorEvent(@Nullable T obj, AjaxRequestTarget target, Component component) {
        super(obj, target, component);
    }


}
