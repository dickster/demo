package forms;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

public class WfEvent<T> implements Serializable /* ApplicationContextAware, ApplicationContextBeanNameAware*/ {

    private T obj;
    private String name;
    // this is really just the next suggested state or happy-path state typically set by BA's.
    //  dev's can override & return whatever they see fit in the getOnSuccessState() method.
    //  e.g. getOnSuccessState() { if (date>today) return "FUTURE" else return "SUCCESS"; }
    private String onSuccessState;
    private Map<String, String> errorState = Maps.newHashMap();

    public WfEvent(@Nonnull T obj) {
        this.obj = obj;
        this.name = obj.toString();
    }

    public <T extends WfEvent> T withNextState(String state) {
        this.onSuccessState = state;
        return (T) this;
    }

    public T getObj() {
        return obj;
    }

    public void setBeanName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getOnSuccessState() {
        return onSuccessState;
    }

}
