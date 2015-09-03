package forms;

import java.util.HashMap;

public class DefaultWorkflowContext extends HashMap implements IWorkflowContext  {

    public DefaultWorkflowContext() {
    }

    @Override
    public Object get(String key) {
        return super.get(key);
    }

    @Override
    public void put(String key, Object value) {
        super.put(key, value);
    }

}
