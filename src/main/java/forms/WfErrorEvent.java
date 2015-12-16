package forms;

import javax.annotation.Nullable;

public class WfErrorEvent<T> extends WfEvent<T> {

    public WfErrorEvent(@Nullable T obj) {
        super(obj);
    }


}
