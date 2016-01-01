package forms.spring;

import forms.model.GenericInsuranceObject;
import forms.validation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Validations {

    @Bean
    public GenericValidationAdapterFactory adapterFactory() {
        return new GenericValidationAdapterFactory();
    }

    @Bean
    public HealthValidation healthValidation() {
        return new HealthValidation();
    }

    @Bean
    public NameValidation nameValidation() {
        return new NameValidation();
    }

    @Bean
    public AddressValidation addressValidation() {
        return new AddressValidation();
    }

    @Bean
    public VehicleValidation vehicleValidation() {
        return new VehicleValidation();
    }

    @Bean
    public IValidation<String> infoValidation() {
        return new ChainedValidation<GenericInsuranceObject,String>()
                .add(healthValidation(), vehicleValidation(), nameValidation());
    }

}
