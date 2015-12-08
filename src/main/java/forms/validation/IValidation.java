package forms.validation;

<<<<<<< Updated upstream
public interface IValidation<R> {

    ValidationResult<R> validate(Object obj);
    ValidationResult<R> newResult();
=======
public interface IValidation<T,R> {

    ValidationResult<R> validate(T obj);
>>>>>>> Stashed changes

}
