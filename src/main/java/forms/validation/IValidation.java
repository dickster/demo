package forms.validation;

import java.io.Serializable;

public interface IValidation<R> extends Serializable {

    ValidationResult<R> validate(Object obj);
    
}
