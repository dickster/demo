package forms.validation;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ChainedValidation<T,R> extends AbstractValidation<T,R> {

    private List<IValidation<R>> validations = Lists.newArrayList();

    @Override
    public @Nonnull ValidationResult<R> validate(@Nonnull Object obj) {
        ValidationResult<R> result = null;
        for (IValidation<R> validation:validations) {
            result = merge(result, validation.validate(obj));
        }
        if (result==null) {
            throw new IllegalStateException("this chainedValidation has no contained validations to delegate to");
        }
        return result;
    }

    @Override
    public ValidationResult<R> newResult() {
        throw new UnsupportedOperationException("this validation should never create results directly.  it will delegate that to it's contained validations.");
    }

    private ValidationResult<R> merge(@Nullable ValidationResult<R> r1, ValidationResult<R> r2) {
        return r1==null ? r2 : r1.merge(r2);
    }

    @Override
    protected ValidationResult<R> doValidation(T input) {
        throw new UnsupportedOperationException("this should not be called for chained validations because it's done by added delegates. ");
    }

    public ChainedValidation add(IValidation<R>... validation) {
        validations.addAll(Lists.newArrayList(validation));
        return this;
    }
}
