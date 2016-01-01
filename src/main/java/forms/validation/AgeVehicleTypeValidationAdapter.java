package forms.validation;

import forms.model.GenericInsuranceObject;
import forms.validation.AgeVehicleTypeValidation.AgeVehicleTypeFields;

public class AgeVehicleTypeValidationAdapter extends ValidationAdapter<GenericInsuranceObject, AgeVehicleTypeFields> {

    protected AgeVehicleTypeValidationAdapter() {
        super(GenericInsuranceObject.class);
    }

    @Override
    public AgeVehicleTypeFields apply(final GenericInsuranceObject obj) {
        return new AgeVehicleTypeFields() {
            @Override public Integer getAge() {
                return obj.getInsured().getAge();
            }

            @Override public String getVehicleType() {
                return obj.getVehicle().getType();
            }
        };
    }

}
