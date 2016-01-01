package forms.validation;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public abstract class AbstractValidation<T, R> implements Serializable, IValidation<R> {

    @Inject
    protected GenericValidationAdapterFactory adapterFactory;

    @Deprecated
    protected AbstractValidation() {
    }

    @Override
    public ValidationResult<R> validate(@Nonnull Object obj) {
        T input = adaptInput(obj);
        return doValidation(input);
    }

    public abstract ValidationResult<R> newResult();

    protected T adaptInput(@Nonnull Object obj) {
        Preconditions.checkArgument(getAdapter(obj).supports(obj), "the adapter does not handle class of type " + obj.getClass());
        T result = getAdapter(obj).convertAndAdapt(obj);
        return result;
    }

    protected abstract ValidationResult<R> doValidation(T input);

    public abstract ValidationAdapter<?, T> getAdapter(Object obj);
}
