package forms.validation;

import javax.annotation.Nonnull;

public interface IValidationAdapters<T> {
    public @Nonnull <I> ValidationAdapter<T,I> on(Class<I> clazz);
}
