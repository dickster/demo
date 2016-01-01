package forms.validation;

import javax.annotation.Nullable;

// made for unit tests when the input type is the same as output type.
public class PassthruValidationAdapter<T> extends ValidationAdapter<T,T> {

    public PassthruValidationAdapter(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public @Nullable T apply(@Nullable T input) {
        return input;
    }

}
