package forms.model;

import com.google.common.base.Preconditions;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.List;

public class SectionModel<T extends Serializable> extends WfCompoundPropertyModel<T> {

    private IModel<List<T>> model;
    private int index = 0;

    public SectionModel(IModel<List<T>> model, int index) {
        super(model.getObject().get(0)); // blargh. fix this...need to pass something into super constructor?
        this.model = model;
        this.index = index;
    }

    public SectionModel(IModel <List<T>> model) {
        this(model,0);
    }

    @Override
    public T getObject() {
        return model.getObject().get(index);
    }

    @Override
    public void setObject(T object) {
        model.getObject().set(index, object);
    }

    @Override
    public void detach() {
        model.detach();
    }

    public SectionModel<T> setIndex(int index) {
        Preconditions.checkArgument(index>=0 && index<model.getObject().size());
        this.index = index;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public void delete(int index) {
        model.getObject().remove(index);
        this.index = Math.min(model.getObject().size()-1, index);
    }

    public int size() {
        return model.getObject().size();
    }

    public void add(T o) {
        List<T> data = model.getObject();
        data.add(o);
        index = data.size()-1;
    }

    public T getObject(int i) {
        return model.getObject().get(i);
    }
}
