package forms.validation;

import com.google.common.base.Preconditions;
import forms.model.GenericInsuranceObject;
import forms.validation.HealthValidation.HealthFields;
import forms.validation.NameValidation.NameFields;
import forms.validation.VehicleValidation.VehicleFields;

import javax.annotation.Nonnull;

public class ValidationAdapterFactory implements IValidationAdapters<GenericInsuranceObject> {

    @Override @Nonnull
    public <I> ValidationAdapter<GenericInsuranceObject,I> on(Class<I> clazz) {
        Preconditions.checkNotNull(clazz, "can't have adapter for null interface class");
//        assert(clazz.isInterface())
        ValidationAdapter< ?, ? > result = null;
        if (clazz.isAssignableFrom(HealthFields.class)) {
            return (ValidationAdapter<GenericInsuranceObject, I>) forHealthFields();
        }
        else if (clazz.isAssignableFrom(NameFields.class)) {
            return (ValidationAdapter<GenericInsuranceObject, I>) forNameFields();
        }
        else if (clazz.isAssignableFrom(VehicleFields.class)) {
            return (ValidationAdapter<GenericInsuranceObject, I>) forVehicleFields();
        }
        throw new IllegalArgumentException("can't find validation adapter for " + clazz.getSimpleName());
    }

    private ValidationAdapter<GenericInsuranceObject, NameFields> forNameFields() {
        return new ValidationAdapter<GenericInsuranceObject, NameFields>(GenericInsuranceObject.class) {
            @Override
            protected NameFields adapt(final GenericInsuranceObject obj) {
                return new NameFields() {
                    @Override public String getFirstName() {
                        return obj.getName().first;
                    }

                    @Override public String getMiddleName() {
                        return obj.getName().middle;
                    }

                    @Override public String getLastName() {
                        return obj.getName().last;
                    }
                };
            }
        };
    }


    private  ValidationAdapter<GenericInsuranceObject, ?> forHealthFields() {
        return new ValidationAdapter<GenericInsuranceObject, HealthFields>(GenericInsuranceObject.class) {
            @Override
            protected HealthFields adapt(final GenericInsuranceObject input) {
                return new HealthFields() {
                    @Override public boolean getSmokes() {
                        return input.getInsured().smokes;
                    }

                    @Override public int getAge() {
                        return input.getInsured().getAge();
                    }

                    @Override public String getFavouriteDrink() {
                        return input.getInsured().drinks;
                    }
                };
            }
        };
    }

    private  ValidationAdapter<GenericInsuranceObject, VehicleFields> forVehicleFields() {
        return new ValidationAdapter<GenericInsuranceObject, VehicleFields>(GenericInsuranceObject.class) {
            @Override
            protected VehicleFields adapt(final GenericInsuranceObject input) {
                return new VehicleFields() {
                    @Override public String getType() {
                        return input.getVehicle().getType();
                    }

                    @Override public int getYear() {
                        return input.getVehicle().year;
                    }

                    @Override public int getNumberOfAccidents() {
                        return input.getInsured().accidents;
                    }
                };
            }
        };
    }

}
