package forms.spring;

import forms.RenderingBehavior;
import forms.behavior.*;
import forms.widgets.config.PhoneNumberConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Behaviors {

    // TODO : put this in a factory configuration...only put behaviors here?
    // can i then put @Scope annotation on whole class?
    @Bean
    public BehaviorFactory behaviorFactory() {
        return new BehaviorFactory();
    }

    // NOTE : *all* behaviors MUST be of Scope("prototype")!!
    //   e.g. @Bean @Scope("prototype") public Foo foo() {...}
    // the factory itself doesn't have to be.
    @Bean @Scope("prototype")
    public AgeOccupationAjaxBehavior ageOccupationAjaxBehavior() {
        return new AgeOccupationAjaxBehavior();
    }

    @Bean @Scope("prototype")
    public NameTypeAheadBehavior nameTypeAheadBehavior() {
        return new NameTypeAheadBehavior();
    }

    @Bean @Scope("prototype")
    public RenderingBehavior renderingBehavior() {
        return new RenderingBehavior();
    }

    @Bean @Scope("prototype")
    public VehicleTypeAheadBehavior vehicleTypeAheadBehavior() {
        return new VehicleTypeAheadBehavior();
    }

    @Bean @Scope("prototype")
    public EmailAjaxBehavior emailAjaxBehavior() {
        return new EmailAjaxBehavior();
    }

    @Bean @Scope("prototype")
    public PhoneNumberAjaxBehavior phoneNumberAjaxBehavior() {
        return new PhoneNumberAjaxBehavior();
    }

}
