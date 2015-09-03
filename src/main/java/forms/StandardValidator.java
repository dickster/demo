package forms;

import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.List;

public class StandardValidator {

    public enum StandardValidatorType {
        NUMERIC_RANGE(), MIN, MAX, EMAIL, PHONE_NUMBER, REQUIRED /*etc...*/
    };

    private final StandardValidatorType type;
    private Constructor<? extends IValidator> constructor;
    private List<Object> args;

    public StandardValidator(StandardValidatorType type, List<Object> args) {
        this.type = type;
        this.args = args;
        try {
            // somehow turn type into constructor.
            this.constructor = EmailAddressValidator.class.getConstructor();
        } catch (NoSuchMethodException e) {

        }
    }

    public @Nonnull IValidator validator() {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new IllegalStateException("can't create validator " + type);
        }
    }
}
