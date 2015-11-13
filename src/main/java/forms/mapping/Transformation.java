package forms.mapping;

import javax.annotation.Nullable;
import java.io.Serializable;

public interface Transformation<T> extends Serializable {
    public @Nullable T transform(@Nullable T o);
    public @Nullable T inverseTransform(@Nullable T o);
}