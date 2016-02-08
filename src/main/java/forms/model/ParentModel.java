package forms.model;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

public class ParentModel<T> implements IModel<T> {

    private final FormComponent component;

    public ParentModel(FormComponent comp) {
        this.component = comp;
    }

    @Override
    public T getObject() {
        return (T) component.getModel().getObject();
    }

    @Override
    public void setObject(T object) {
        component.getModel().setObject(object);
    }

    @Override
    public void detach() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
