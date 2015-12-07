package forms.validation;

// made for unit tests when the input type is the same as output type.
public class PassthruValidationAdapter<T> extends ValidationAdapter<T,T> {

    public PassthruValidationAdapter(Class<T> clazz) {
        super(clazz);
    }

    @Override
    protected T adapt(T input) {
        return input;
    }

}
