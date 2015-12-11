package forms.validation;

public interface IValidation<R> {

    ValidationResult<R> validate(Object obj);
    
}
