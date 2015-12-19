package forms.validation;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.io.Serializable;

public abstract class ValidationAdapter<F, T> implements Serializable {

    public static final ValidationAdapter NA = new ValidationAdapter(String.class) {
        @Override protected Object adapt(Object input) {
            throw new UnsupportedOperationException("this should never be called. just used as a placeholder");
        }
    };

    private Class<F> clazz;

    protected ValidationAdapter(Class<F> clazz) {
        this.clazz = clazz;
    }

    public final boolean supports(@Nonnull Object obj) {
        return getSourceClass().isAssignableFrom(obj.getClass());
    }

    public final Class<F> getSourceClass() {
        return clazz;
    }

    public final T convertAndAdapt(Object obj) {
        Preconditions.checkState(supports(obj), "this adapter is meant for " + getSourceClass().getSimpleName() + " but you have passed an object of type " + obj.getClass().getSimpleName());
        F cast = getSourceClass().cast(obj);
        return adapt(cast);
    }

    protected abstract T adapt(F input);

}
