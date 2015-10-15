package forms;

import org.apache.wicket.model.IModel;

import java.io.Serializable;

public interface IWorkflowContext<M> extends Serializable {

    public Object get(String key);

    public void put(String key, Object value);

    public IModel<M> getModel();

}
