package forms;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.ListIterator;

public class ChainedTransformation<T> implements Transformation<T> {

    private List<Transformation> transformations = Lists.newArrayList();


    public ChainedTransformation(Transformation... transformations) {
        chain(transformations);
    }

    public ChainedTransformation(Transformation transformation) {
        chain(transformation);
    }

    public ChainedTransformation chain(Transformation transformation) {
        transformations.add(transformation);
        return this;
    }

    public ChainedTransformation chain(Transformation... transformation) {
        transformations.addAll(Lists.newArrayList(transformation));
        return this;
    }

    @Override
    public Object transform(Object o) {
        Object value = o;
        for (ListIterator<Transformation> i = transformations.listIterator(); i.hasNext(); ) {
            Transformation t = i.next();
            value = t.transform(value);
        }
        return value;
    }

    @Override
    public Object inverseTransform(Object o) {
        // IMPORTANT : inverse transformations must be done in reverse order!
        Object value = o;
        for (ListIterator<Transformation> i = transformations.listIterator(transformations.size()); i.hasPrevious(); ) {
            Transformation t = i.previous();
            value = t.inverseTransform(o);
        }
        return value;
    }

}
