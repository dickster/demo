package forms;

import org.apache.wicket.Component;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// just a sample instance of the factory with some convenience methods.
//  you'll want to extend this to do the actual factory work.
// e.g. make one that calls a groovy script?
public class DefaultValidatorFactory implements ValidatorFactory {

    @Override
    public @Nonnull IValidator create(String key, final Component c) {
        // if key==blah then return blargh. etc...
        // e.g. key = "vin-validation.groovy" or key="numeric(0..100)"
        // so you might have to parse key to figure out what it means.

        StandardValidator sv = parse(key);
        if (sv==null) {
            return new IValidator() {
                @Override
                public void validate(IValidatable validatable) {
                    ; // do nothing...just an example/template.
                }
            };
        } else {
            return sv.validator();
        }

    }

    private @Nullable StandardValidator parse(String key) {
        // use javacc to parse stuff like....
        //      MIN(0), RANGE(0,100) INCLUSIVE_RANGE(10,20), DATE_RANGE("today", "jan 21, 2015"), EMAIL, etc..
        //  into proper validators.
        return null;
    }


}

