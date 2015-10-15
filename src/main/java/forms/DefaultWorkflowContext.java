package forms;

import org.apache.wicket.model.IModel;

import java.util.HashMap;

public class DefaultWorkflowContext<M> extends HashMap implements IWorkflowContext<M> {

    private static final String DATA = "$$DATA$$";

    public DefaultWorkflowContext() {
    }

    public DefaultWorkflowContext(IModel<M> model) {
        withModel(model);
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
    public IModel<M> getModel() {
        return (IModel<M>) super.get(DATA);
    }

    public void withModel(IModel<M> model) {
        super.put(DATA, model);
    }

}
