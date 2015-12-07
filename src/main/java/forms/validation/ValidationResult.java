package forms.validation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class ValidationResult<T> {
    private List<T> errors = Lists.newArrayList();

    public ValidationResult() {
    }

    public int getNumberErrors() {
        return errors.size();
    }

    public List<T> getErrors() {
        return ImmutableList.copyOf(errors);
    }

    public final ValidationResult<T> fail(T reason) {
        addResult(reason);
        return this;
    }

    public final ValidationResult<T> pass(T reason) {
        addResult(reason);
        return this;
    }

    private void addResult(T reason) {
        errors.add(reason);
    }

    public boolean isSuccess() {
        return errors.size()==0;
    }

    public ValidationResult<T> merge(ValidationResult<T> validate) {
        errors.addAll(validate.getErrors());
        return this;
    }


    @Override
    public String toString() {
        return "ValidationResult{" +
                "errors=" + errors +
                "success=" + (errors.size()==0) +
                '}';
    }
}
