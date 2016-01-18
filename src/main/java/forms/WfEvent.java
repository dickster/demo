package forms;

import com.google.common.collect.Maps;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

public class WfEvent<T> implements Serializable {

    private Component component;
    private AjaxRequestTarget target;
    private T obj;
    private String name;
    private Map<String, String> errorState = Maps.newHashMap();

    // this is really just the next suggested state or happy-path state typically set by BA's.
    //  dev's can override & return whatever they see fit in the getOnSuccessState() method.
    //  e.g. getOnSuccessState() { if (date>today) return "FUTURE" else return "SUCCESS"; }
    private String onSuccessState;

    public WfEvent(@Nullable T obj) {
        this(obj, null, null);
    }

    public WfEvent(@Nullable T obj, AjaxRequestTarget target, Component component) {
        this.obj = obj;
        this.name = obj==null ? "null event" : obj.toString();
        this.target = target;
        this.component = component;
    }

    public <T extends WfEvent> T withNextState(String state) {
        this.onSuccessState = state;
        return (T) this;
    }

    public T getObj() {
        return obj;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getOnSuccessState() {
        return onSuccessState;
    }

    public Component getComponent() {
        return component;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
