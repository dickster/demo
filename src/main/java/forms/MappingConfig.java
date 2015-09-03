package forms;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.apache.wicket.model.PropertyModel;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

public class MappingConfig<A,T> implements Serializable {

    //A-->Transform.
    //T-->InverseTransform.

    private Map<String, Function> acordTransformations = Maps.newHashMap();
    private Map<String, Function> implTransformations = Maps.newHashMap();

    @Nullable
    public Object mapFromAcordToImpl(A acord, String a, T impl, String t) {
        Object value = new PropertyModel(acord, a).getObject();
        value = transform(acordTransformations.get(a),value);
        new PropertyModel(impl, t).setObject(value);
        return value;
    }

    @Nullable
    private Object transform(Function f, Object value) {
        return (f!=null) ?
             f.apply(value) :
             value;
    }

    @Nullable
    public Object mapFromImplToAcord(A acord, String a, T impl, String t) {
        Object value = new PropertyModel(impl, t).getObject();
        value = transform(implTransformations.get(t), value);
        new PropertyModel(acord, a).setObject(value);
        return value;
    }

    // alias if you prefer different naming style.
    @Nullable
    public Object mapToAcordFromImpl(A acord, String a, T impl, String t) {
        return mapFromImplToAcord(acord, a, impl, t);
    }

    public void addTransformation(String acord, String impl, Transformation transformation) {
        acordTransformations.put(acord, fn(transformation));
        implTransformations.put(impl, inverseFn(transformation));
    }

    private Function fn(final Transformation transformation) {
        return new Function() {
            @Nullable @Override public Object apply(@Nullable Object o) {
                return transformation.transform(o);
            }
        };
    }

    private Function inverseFn(final Transformation transformation) {
        return new Function() {
            @Nullable @Override public Object apply(@Nullable Object o) {
                return transformation.inverseTransform(o);
            }
        };
    }


}
