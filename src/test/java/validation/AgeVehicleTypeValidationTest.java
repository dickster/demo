package validation;

import forms.validation.AgeVehicleTypeValidation;
import forms.validation.AgeVehicleTypeValidation.AgeVehicleTypeFields;
import forms.validation.PassthruValidationAdapter;
import forms.validation.ValidationResult;
import junit.framework.TestCase;
import org.junit.Test;

public class AgeVehicleTypeValidationTest extends TestCase {

        @Test
        public void testDoValidation() {
            AgeVehicleTypeValidation validation = (AgeVehicleTypeValidation) new AgeVehicleTypeValidation()
                    .withAdapter(new PassthruValidationAdapter<AgeVehicleTypeFields>(AgeVehicleTypeFields.class));

            ValidationResult<Integer> result;
            result = validation.validate(new AgeVehicleTypeFields(35, "Sports"));
            assertTrue(result.isSuccess());
            assertTrue(result.getNumberErrors()==0);

            result = validation.validate(new AgeVehicleTypeFields(29, "Sports"));
            assertTrue(!result.isSuccess());
            assertTrue(result.getNumberErrors()==1);
            assertTrue(result.getErrors().get(0).equals(202));

            result = validation.validate(new AgeVehicleTypeFields(29, "Pickup"));
            assertTrue(result.isSuccess());
            assertTrue(result.getNumberErrors()==0);

            result = validation.validate(new AgeVehicleTypeFields(29, "PICKUP"));
            assertTrue(result.isSuccess());
            assertTrue(result.getNumberErrors()==0);

            result = validation.validate(new AgeVehicleTypeFields(66, "PickUP"));
            assertTrue(!result.isSuccess());
            assertTrue(result.getNumberErrors()==1);
            assertTrue(result.getErrors().get(0).equals(999));

            result = validation.validate(new AgeVehicleTypeFields(16, "SPOrts"));
            assertTrue(!result.isSuccess());
            assertTrue(result.getNumberErrors()==2);
            assertTrue(result.getErrors().contains(202));
            assertTrue(result.getErrors().contains(43));
        }


}
