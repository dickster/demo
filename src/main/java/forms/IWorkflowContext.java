package forms;

import java.io.Serializable;

public interface IWorkflowContext extends Serializable {

    public Object get(String key);

    public void put(String key, Object value);
}
