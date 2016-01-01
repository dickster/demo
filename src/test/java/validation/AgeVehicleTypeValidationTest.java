package validation;

import forms.validation.AgeVehicleTypeValidation;
import forms.validation.AgeVehicleTypeValidation.AgeVehicleTypeFields;
import forms.validation.PassthruValidationAdapter;
import forms.validation.ValidationAdapter;
import forms.validation.ValidationResult;
import junit.framework.TestCase;
import org.junit.Test;

public class AgeVehicleTypeValidationTest extends TestCase {

        @Test
        public void testDoValidation() {
            AgeVehicleTypeValidation validation = new AgeVehicleTypeValidation() {
                @Override
                public ValidationAdapter<?, AgeVehicleTypeFields> getAdapter(Object obj) {
                    return new PassthruValidationAdapter<AgeVehicleTypeFields>(AgeVehicleTypeFields.class);
                }
            };

            ValidationResult<Integer> result;
            result = validation.validate(testData(35, "Sports"));
            assertTrue(result.isSuccess());
            assertTrue(result.getNumberErrors()==0);

            result = validation.validate(testData(29, "Sports"));
            assertTrue(!result.isSuccess());
            assertTrue(result.getNumberErrors()==1);
            assertTrue(result.getErrors().get(0).equals(202));

            result = validation.validate(testData(29, "Pickup"));
            assertTrue(result.isSuccess());
            assertTrue(result.getNumberErrors()==0);

            result = validation.validate(testData(29, "PICKUP"));
            assertTrue(result.isSuccess());
            assertTrue(result.getNumberErrors()==0);

            result = validation.validate(testData(66, "PickUP"));
            assertTrue(!result.isSuccess());
            assertTrue(result.getNumberErrors()==1);
            assertTrue(result.getErrors().get(0).equals(999));

            result = validation.validate(testData(16, "SPOrts"));
            assertTrue(!result.isSuccess());
            assertTrue(result.getNumberErrors()==2);
            assertTrue(result.getErrors().contains(202));
            assertTrue(result.getErrors().contains(43));
        }

    private AgeVehicleTypeFields testData(final int age, final String type) {
        return new AgeVehicleTypeFields() {
            @Override public Integer getAge() { return age; }
            @Override public String getVehicleType() { return type; }
        };
    }


}
