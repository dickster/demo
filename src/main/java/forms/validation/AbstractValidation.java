package forms.validation;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.io.Serializable;

public abstract class AbstractValidation<T, R> implements Serializable, IValidation<R> {

    public ValidationAdapter<?,T> adapter;

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
        Preconditions.checkArgument(adapter.supports(obj), "the adapter does not handle class of type " + obj.getClass());
        T result = adapter.convertAndAdapt(obj);
        return result;
    }

    protected abstract ValidationResult<R> doValidation(T input);
}
