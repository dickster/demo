package forms.validation;

import demo.Address;

public class AddressValidation extends AbstractValidation<Address, String> {

    public AddressValidation() {
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

    @Override
    public ValidationAdapter<?, Address> getAdapter(Object obj) {
        return adapterFactory.adapt(obj, Address.class);
    }


}
