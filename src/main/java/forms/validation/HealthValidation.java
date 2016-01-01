package forms.validation;

import forms.validation.HealthValidation.HealthFields;

// A --> B

public class HealthValidation extends AbstractValidation<HealthFields, String> {

    public HealthValidation() {
    }

    @Override
    protected ValidationResult<String> doValidation(HealthFields input) {
        ValidationResult<String> result = newResult();
        if ("coke".equalsIgnoreCase(input.getFavouriteDrink())) {
            result.fail("you drink coke");
        }
        if (input.getAge()>60) {
            result.fail("you are over 60");
        }
        if (input.getSmokes()&& input.getAge()>45) {
            result.fail("you are over 45 and smoke.");
        }
        return result;
    }

    @Override
    public ValidationResult<String> newResult() {
        return new ValidationResult<String>();
    }

    @Override
    public ValidationAdapter<?, HealthFields> getAdapter(Object obj) {
        return adapterFactory.adapt(obj, HealthFields.class);
    }

    public static interface HealthFields {
        public boolean getSmokes();
        public int getAge();
        public String getFavouriteDrink();
    }

}
