package forms.validation;

import forms.model.GenericInsuranceObject;
import forms.validation.NameValidation.NameFields;

public class NameValidation extends AbstractValidation<NameFields, String> {

    public NameValidation(ValidationAdapter<GenericInsuranceObject, NameFields> adapter) {
        super(adapter);
    }

    @Override
    protected ValidationResult<String> doValidation(NameFields input) {
        ValidationResult<String> result = newResult();
        if ("derek".equalsIgnoreCase(input.getFirstName())) {
            result.fail("your first name is derek");
        }
        if ("william".equalsIgnoreCase(input.getMiddleName())) {
            result.fail("your middle name is william");
        }
        if ("dick".equalsIgnoreCase(input.getLastName())) {
            result.fail("your last name is dick");
        }
        return result;
    }

    @Override
    public ValidationResult<String> newResult() {
        return new ValidationResult<String>();
    }


    public static interface NameFields {
        public String getFirstName();
        public String getMiddleName();
        public String getLastName();
    }

}
