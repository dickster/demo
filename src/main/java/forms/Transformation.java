package forms;

import java.io.Serializable;

public interface Transformation<T> extends Serializable {
    public Object transform(T o);
    public Object inverseTransform(T o);
}