package forms.spring;

import forms.model.GenericInsuranceObject;
import forms.validation.ChainedValidation;
import forms.validation.HealthValidation;
import forms.validation.HealthValidation.HealthFields;
import forms.validation.IValidation;
import forms.validation.NameValidation;
import forms.validation.NameValidation.NameFields;
import forms.validation.ValidationAdapterFactory;
import forms.validation.VehicleValidation;
import forms.validation.VehicleValidation.VehicleFields;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Validations {

    @Bean
    public ValidationAdapterFactory adapterFactory() {
        return new ValidationAdapterFactory();
    }

    @Bean
    public HealthValidation healthValidation() {
        return new HealthValidation(adapterFactory().on(HealthFields.class));
    }

    @Bean
    public NameValidation nameValidation() {
        return new NameValidation(adapterFactory().on(NameFields.class));
    }

    @Bean
    public VehicleValidation vehicleValidation() {
        return new VehicleValidation(adapterFactory().on(VehicleFields.class));
    }

    @Bean
    public IValidation<String> infoValidation() {
        return new ChainedValidation<GenericInsuranceObject,String>()
                .add(healthValidation(), vehicleValidation(), nameValidation());
    }

}
