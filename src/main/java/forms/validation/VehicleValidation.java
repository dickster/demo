package forms.validation;
import forms.model.GenericInsuranceObject;
import forms.validation.VehicleValidation.VehicleFields;

public class VehicleValidation extends AbstractValidation<VehicleFields, String> {

    public VehicleValidation(ValidationAdapter<GenericInsuranceObject, VehicleFields> adapter) {
        super(adapter);
    }

    @Override
    protected ValidationResult<String> doValidation(VehicleFields input) {
        ValidationResult<String> result = newResult();
        if ("BMW".equalsIgnoreCase(input.getType())) {
            result.fail("you drive a BMW");
        }
        if (input.getYear()<1977) {
            result.fail("your car is made before 1977");
        }
        if (input.getNumberOfAccidents()>2) {
            result.fail("you have more than 2 accidents");
        }
        return result;
    }

    @Override
    public ValidationResult<String> newResult() {
        return new ValidationResult<String>();
    }


    public static interface VehicleFields {
        public String getType();
        public int getYear();
        public int getNumberOfAccidents();
    }

}
