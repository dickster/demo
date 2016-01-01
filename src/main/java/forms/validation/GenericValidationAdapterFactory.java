package forms.validation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import demo.Address;
import forms.model.GenericInsuranceObject;
import forms.validation.HealthValidation.HealthFields;
import forms.validation.NameValidation.NameFields;
import forms.validation.VehicleValidation.VehicleFields;

import javax.annotation.Nonnull;
import java.util.Map;

// turns workflow data in interfaced objects used by validators.
public class GenericValidationAdapterFactory implements IValidationAdapters<GenericInsuranceObject> {

    private final Map<Class, ValidationAdapter<GenericInsuranceObject,?>> adapters = Maps.newHashMap();

    public GenericValidationAdapterFactory() {
        adapters.put(HealthFields.class, forHealthFields());
        adapters.put(Address.class, forAddress());
        adapters.put(HealthFields.class, forNameFields());
        adapters.put(HealthFields.class, forVehicleFields());
    }

    @Override @Nonnull
    public <I> ValidationAdapter<GenericInsuranceObject,I> adapt(Object from, Class<I> to) {
        Preconditions.checkArgument(from instanceof GenericInsuranceObject);
        Preconditions.checkNotNull(to, "can't have adapter for null target class");
        ValidationAdapter<GenericInsuranceObject, I> adapter = (ValidationAdapter<GenericInsuranceObject, I>) adapters.get(to);
        if (adapter==null) {
            throw new IllegalArgumentException("can't find validation adapter for " + to.getSimpleName());
        }
        return adapter;
    }

    private ValidationAdapter<GenericInsuranceObject, Address> forAddress() {
        return new ValidationAdapter<GenericInsuranceObject, Address>(GenericInsuranceObject.class) {
            @Override
            public Address apply(GenericInsuranceObject input) {
                return input.getInsured().address;
            }
        };
    }

    private ValidationAdapter<GenericInsuranceObject, NameFields> forNameFields() {
        return new ValidationAdapter<GenericInsuranceObject, NameFields>(GenericInsuranceObject.class) {
            @Override
            public NameFields apply(final GenericInsuranceObject obj) {
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


    private ValidationAdapter<GenericInsuranceObject,HealthFields> forHealthFields() {
        return new ValidationAdapter<GenericInsuranceObject, HealthFields>(GenericInsuranceObject.class) {
            @Override
            public HealthFields apply(final GenericInsuranceObject input) {
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

    private ValidationAdapter<GenericInsuranceObject, VehicleFields> forVehicleFields() {
        return new ValidationAdapter<GenericInsuranceObject, VehicleFields>(GenericInsuranceObject.class) {
            @Override
            public VehicleFields apply(final GenericInsuranceObject input) {
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
