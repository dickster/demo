package forms;

import org.apache.wicket.model.IModel;

public interface IHasMultipleModels {
    void setDefaultModels(IModel<?>... models);
}
