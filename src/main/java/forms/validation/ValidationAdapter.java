package forms.validation;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

public abstract class ValidationAdapter<F, T> implements Serializable, Function<F, T> {

    public static final ValidationAdapter NA = new ValidationAdapter(String.class) {
        @Override public Object apply(Object input) {
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
        return apply(cast);
    }

    @Override
    public @Nullable abstract T apply(@Nullable F input);
}
