package forms.validation;

import forms.validation.AgeVehicleTypeValidation.AgeVehicleTypeFields;

public class AgeVehicleTypeValidation extends AbstractValidation<AgeVehicleTypeFields, Integer> {

    private static final Integer ERROR_43 = 43;
    private static final Integer ERROR_202 = 202;
    private static final Integer ERROR_999 = 999;

    public AgeVehicleTypeValidation() {
    }

    @Override
    protected ValidationResult<Integer> doValidation(AgeVehicleTypeFields input) {
        ValidationResult<Integer> result = newResult();
        if (input.getAge()<18) {
            result.fail(ERROR_43);
        }
        if ("SPORTS".equalsIgnoreCase(input.getVehicleType()) && input.getAge()<30) {
            result.fail(ERROR_202);
        }
        if ("PICKUP".equalsIgnoreCase(input.getVehicleType()) && input.getAge()>65) {
            result.fail(ERROR_999);
        }
        return result;
    }

    @Override
    public ValidationAdapter<?, AgeVehicleTypeFields> getAdapter(Object obj) {
        return adapterFactory.adapt(obj,AgeVehicleTypeFields.class);
    }

    @Override
    public ValidationResult<Integer> newResult() {
        return new ValidationResult<Integer>();
    }

    public static interface AgeVehicleTypeFields {
        public Integer getAge();
        public String getVehicleType();
    }

}
