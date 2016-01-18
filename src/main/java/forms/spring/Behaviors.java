package forms.spring;

import forms.RenderingBehavior;
import forms.behavior.AgeOccupationAjaxBehavior;
import forms.behavior.NameTypeAheadBehavior;
import forms.behavior.VehicleTypeAheadBehavior;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Behaviors {

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

}
