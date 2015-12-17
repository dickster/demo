package forms.validation;

import demo.Address;
import forms.model.GenericInsuranceObject;

public class AddressValidation extends AbstractValidation<Address, String> {

    public AddressValidation(ValidationAdapter<GenericInsuranceObject, Address> adapter) {
        super(adapter);
    }

    @Override
    public ValidationResult<String> newResult() {
        return new ValidationResult<String>();
    }

    @Override
    protected ValidationResult<String> doValidation(Address input) {
        ValidationResult<String> result = newResult();
        if ("ONTARIO".equalsIgnoreCase(input.getProvince())) {
            result.fail("coverage not provided in Ontario");
        }
        return result;
    }


}
