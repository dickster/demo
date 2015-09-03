package forms;

import org.apache.wicket.Component;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;

public interface ValidatorFactory {
    @Nonnull IValidator create(String key, Component c);
}
