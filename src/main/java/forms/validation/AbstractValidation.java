package forms.validation;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.io.Serializable;

public abstract class AbstractValidation<T, R> implements Serializable, IValidation<R> {

    private ValidationAdapter<?,T> adapter;

    @Deprecated // take out adapter...make it transient spring injected thing.
    protected AbstractValidation(ValidationAdapter<?, T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public ValidationResult<R> validate(@Nonnull Object obj) {
        T input = adaptInput(obj);
        return doValidation(input);
    }

    public abstract ValidationResult<R> newResult();

    protected T adaptInput(@Nonnull Object obj) {
        Preconditions.checkArgument(getAdapter().supports(obj), "the adapter does not handle class of type " + obj.getClass());
        T result = getAdapter().convertAndAdapt(obj);
        return result;
    }

    protected abstract ValidationResult<R> doValidation(T input);

    public ValidationAdapter<?, T> getAdapter() {
        return adapter;
    }
}
