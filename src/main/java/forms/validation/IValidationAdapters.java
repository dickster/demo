package forms.validation;

import javax.annotation.Nonnull;

public interface IValidationAdapters<F> {

    @Nonnull <T> ValidationAdapter<?,T> adapt(Object from, Class<T> to);
}
