package forms.validation;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class ChainedValidation<T,R> extends AbstractValidation<T,R> {

    private List<IValidation<R>> validations = Lists.newArrayList();

    @Override
    protected ValidationResult<R> doValidation(T input) {
        ValidationResult<R> result = newResult();
        for (IValidation<R> validation:validations) {
            result.merge(validation.validate(input));
        }
        return result;
    }

    public ChainedValidation chain(IValidation<R> validation) {
        validations.add(validation);
        return this;
    }
}
