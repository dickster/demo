package forms.validation;

import forms.model.GermanInsuranceObject;
import forms.validation.AgeVehicleTypeValidation.AgeVehicleTypeFields;

public class AgeVehicleTypeValidationAdapter extends ValidationAdapter<GermanInsuranceObject, AgeVehicleTypeFields> {

    protected AgeVehicleTypeValidationAdapter(Class<GermanInsuranceObject> clazz) {
        super(clazz);
    }

    @Override
    public boolean supports(Object clazz) {
        return clazz.equals(GermanInsuranceObject.class);
    }

    @Override
    public AgeVehicleTypeFields adapt(GermanInsuranceObject obj) {
        return new AgeVehicleTypeFields(obj.getInsured().getAge(),
                    obj.getVehicle().getType());
    }

}
