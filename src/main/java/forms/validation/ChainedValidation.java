package forms.validation;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ChainedValidation<T,R> implements IValidation<R> {

    private List<IValidation<R>> validations = Lists.newArrayList();

    public ChainedValidation() {
        super();
    }

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

    private ValidationResult<R> merge(@Nullable ValidationResult<R> r1, ValidationResult<R> r2) {
        return r1==null ? r2 : r1.merge(r2);
    }

    public ChainedValidation<T, R> add(IValidation<R>... validation) {
        validations.addAll(Lists.newArrayList(validation));
        return this;
    }
}
