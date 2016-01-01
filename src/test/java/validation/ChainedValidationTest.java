package validation;

import forms.validation.AbstractValidation;
import forms.validation.AgeVehicleTypeValidation;
import forms.validation.AgeVehicleTypeValidation.AgeVehicleTypeFields;
import forms.validation.ChainedValidation;
import forms.validation.IValidation;
import forms.validation.ValidationAdapter;
import forms.validation.ValidationResult;
import junit.framework.TestCase;
import org.junit.Test;

public class ChainedValidationTest extends TestCase {

    @Test
    public void testDoValidation() {

        IValidation<Integer> ageTypeValidation = new AgeVehicleTypeValidation() {
            @Override
            public ValidationAdapter<?, AgeVehicleTypeFields> getAdapter(Object obj) {
                return new TestDataAgeTypeAdapter();
            }
        };

        IValidation<Integer> customValidation = new AbstractValidation<TestData, Integer>() {
            @Override
            protected ValidationResult<Integer> doValidation(TestData input) {
                ValidationResult<Integer> result = newResult();
                if (input.getNumberOfAccidents() >1) {
                    result.fail(-1);
                }
                return result;
            }

            @Override
            public ValidationAdapter<?, TestData> getAdapter(Object obj) {
                return new TestDataNumberOfAccientsAdapter();
            }

            @Override
            public ValidationResult<Integer> newResult() {
                return new ValidationResult<Integer>();
            }

        };

        IValidation<Integer> chain = new ChainedValidation<TestData, Integer>()
                                                .add(ageTypeValidation, customValidation);

        ValidationResult<Integer> result;
        result = chain.validate(new TestData(33, "PICKUP", 1));
        assertTrue(result.isSuccess());

        result = chain.validate(new TestData(19, "PICKUP", 1));
        assertTrue(result.isSuccess());

        result = chain.validate(new TestData(19, "PICKUP", 2));
        assertFalse(result.isSuccess());
        assertTrue(result.getNumberErrors()==1);
        assertTrue(result.getErrors().get(0)==-1);

        result = chain.validate(new TestData(35, "SPORTS", 0));
        assertTrue(result.isSuccess());

        result = chain.validate(new TestData(17, "SPORTS", 1));
        assertFalse(result.isSuccess());

        result = chain.validate(new TestData(88, "PICKUP", 1));
        assertFalse(result.isSuccess());
        assertTrue(result.getNumberErrors() == 1);
        assertTrue(result.getErrors().contains(999));

        result = chain.validate(new TestData(88, "PICKUP", 0));
        assertFalse(result.isSuccess());
        assertTrue(result.getNumberErrors()==1);
        assertTrue(result.getErrors().contains(999));

        result = chain.validate(new TestData(88, "PICKUP", 2));
        assertFalse(result.isSuccess());
        System.out.println(result);
        assertTrue(result.getNumberErrors() == 2);
        assertTrue(result.getErrors().contains(999));
        assertTrue(result.getErrors().contains(-1));
    }


    class TestDataAgeTypeAdapter extends ValidationAdapter<TestData, AgeVehicleTypeFields> {

        protected TestDataAgeTypeAdapter() {
            super(TestData.class);
        }

        @Override
        public AgeVehicleTypeFields apply(final TestData obj) {
            return new AgeVehicleTypeFields() {
                @Override public Integer getAge() { return obj.getAge(); }
                @Override public String getVehicleType() { return obj.getType(); }
            };
        }

    }

    class TestDataNumberOfAccientsAdapter extends ValidationAdapter<TestData, TestData> {

        protected TestDataNumberOfAccientsAdapter() {
            super(TestData.class);
        }

        @Override
        public TestData apply(TestData obj) {
            return obj;
        }

    }


    class TestData  {
        private int age;
        private String type;
        private Integer numberOfAccidents;

        public TestData(int age, String type, Integer numberOfAccidents) {
            this.age = age;
            this.type = type;
            this.numberOfAccidents = numberOfAccidents;
        }

        public int getAge() { return age; }

        public String getType() { return type; }

        public Integer getNumberOfAccidents() { return numberOfAccidents; }
    }

}
