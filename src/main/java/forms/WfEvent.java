package forms;

import javax.annotation.Nonnull;
import java.io.Serializable;

public abstract class WfEvent implements Serializable /*, BeanNameAware*/ {

    private String name;

    public WfEvent() {
    }

    void setBeanName(String name)  {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Nonnull
    public abstract WfState getTransitionState();


}
