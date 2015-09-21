package forms;

import org.apache.wicket.model.IModel;

import java.util.HashMap;

public class DefaultWorkflowContext<M extends IModel<?>> extends HashMap implements IWorkflowContext<M>  {

    private static final String DATA = "$$DATA$$";

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

    @Override
    public M getModel() {
        return (M) super.get(DATA);
    }

    @Override
    public void setModel(M model) {
        super.put(DATA, model);
    }

}
