package forms;

import org.apache.wicket.validation.Validatable;

public interface ValidatingForm {
    void validate(Validatable<?> validatable);
}



// widgetConfig : validations = NUMERIC(0..100) (0>...100)  (0..<100)
//                custom(groovy file)
// form validations


