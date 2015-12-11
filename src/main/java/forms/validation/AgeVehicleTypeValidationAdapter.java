package forms.validation;

import forms.model.GermanInsuranceObject;
import forms.validation.AgeVehicleTypeValidation.AgeVehicleTypeFields;

public class AgeVehicleTypeValidationAdapter extends ValidationAdapter<GermanInsuranceObject, AgeVehicleTypeFields> {

    protected AgeVehicleTypeValidationAdapter() {
        super(GermanInsuranceObject.class);
    }

    @Override
    public AgeVehicleTypeFields adapt(final GermanInsuranceObject obj) {
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
