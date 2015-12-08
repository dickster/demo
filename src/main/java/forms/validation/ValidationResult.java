package forms.validation;

<<<<<<< Updated upstream
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
=======
import com.google.common.base.Preconditions;

public class ValidationResult<R>  {

    private Boolean valid = null;
    private R result;

    public ValidationResult(R r) {
    }

    public R getResult() {
        return result;
    }

    public boolean isValid() {
        Preconditions.checkState(valid==null, "result value hasn't been set yet.");
        return valid;
    }

    public ValidationResult<R> invalid() {
        Preconditions.checkState(valid==null, "can't change immutable result");
        this.valid = false;
        return this;
    }

    public ValidationResult<R> valid() {
        Preconditions.checkState(valid==null, "can't change immutable result");
        this.valid = true;
        return this;
    }
>>>>>>> Stashed changes
}
