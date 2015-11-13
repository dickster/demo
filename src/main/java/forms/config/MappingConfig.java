package forms.config;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import forms.mapping.Transformation;
import org.apache.wicket.model.PropertyModel;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

public class MappingConfig<A,T> implements Serializable {

    //A-->Transform-->T
    //T-->InverseTransform-->A

    private final Transformation nopTransformation = new Transformation() {
        @Override public Object transform(@Nullable Object o) {
            return o;
        }
        @Override public Object inverseTransform(@Nullable Object o) {
            return o;
        }
    };

    private BiMap<String, String> properties = HashBiMap.create();
    private Map<String, Function> implTransformations = Maps.newHashMap();
    private Map<String, Function> acordTransformations = Maps.newHashMap();

    public MappingConfig() {
    }

    @Nullable
    private Object transform(Function f, Object value) {
        return (f!=null) ?
             f.apply(value) :
             value;
    }

    public void addTransformation(String acord, String impl, Transformation transformation) {
        properties.put(acord, impl);
        acordTransformations.put(acord, fn(transformation));
        implTransformations.put(impl, inverseFn(transformation));
    }

    public void addMapping(String acord, String impl) {
        addTransformation(acord, impl, nopTransformation);
    }

    private Function fn(final Transformation transformation) {
        return new Function() {
            @Nullable @Override public Object apply(@Nullable Object o) {
                return transformation==null ? o : transformation.transform(o);
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

    public final T mapFromAcordToImpl(A a, T t) {
        for (String acordProperty:acordTransformations.keySet()) {
            String implProperty = properties.get(acordProperty);
            Object value = new PropertyModel(a, acordProperty).getObject();
            Function fn = acordTransformations.get(a);
            value = transform(fn, value);
            new PropertyModel(t, implProperty).setObject(value);
        }
        t = mapCustomFromAcordToImpl(a,t);
        return t;
    }

    protected T mapCustomFromAcordToImpl(A a, T t) {
        // TODO : should use read-only interface for acord obj.
        // override if you have some complex transformations here.
        return t;
    }

    protected A mapCustomFromImplToAcord(A a, T t) {
        // TODO : should use read-only interface for impl obj.
        // override if you have some complex transformations here.
        return a;
    }

    // alias if you prefer different naming style.
    @Nullable
    public A mapToAcordFromImpl(A acord, T t) {
        return mapFromImplToAcord(acord, t);
    }

    public A mapFromImplToAcord(A a, T t) {
        BiMap<String, String> implToAcordMap = properties.inverse();
        for (String implProperty:implTransformations.keySet()) {
            Object value = new PropertyModel(t, implProperty).getObject();
            Function fn = implTransformations.get(t);
            value = transform(fn, value);
            String acordProperty = implToAcordMap.get(implProperty);
            new PropertyModel(a, acordProperty).setObject(value);
        }
        a = mapCustomFromImplToAcord(a,t);
        return a;
    }

}
