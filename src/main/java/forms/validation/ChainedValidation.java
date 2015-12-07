package forms.validation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.List;

public class ChainedValidation<T,R> extends AbstractValidation<T,R> {

    private List<IValidation<R>> validations = Lists.newArrayList();

    @Override
    public ValidationResult<R> validate(@Nonnull Object obj) {
        ValidationResult<R> result = newResult();
        for (IValidation<R> validation:validations) {
            result.merge(validation.validate(obj));
        }
        return result;
    }

    @Override
    protected ValidationResult<R> doValidation(T input) {
        throw new UnsupportedOperationException("this should not be called for chained validations because it's done by added delegates. ");
    }

    @Override
    public ValidationResult<R> newResult() {
        Preconditions.checkState(validations.size()>0, "you can't create a result without any validations");
        return validations.get(0).newResult();
    }

    public ChainedValidation add(IValidation<R>... validation) {
        validations.addAll(Lists.newArrayList(validation));
        return this;
    }
}
