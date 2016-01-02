package forms.spring;

import forms.ajax.AgeOccupationAjaxBehavior;
import forms.ajax.PaymentMethodAjaxBehavior;
import forms.model.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AjaxBehaviors {

    // NOTE : *all* ajax behaviors MUST be of Scope("prototype")!!
    //   e.g. @Bean @Scope("prototype") public Foo foo() {...}

    @Bean
    public AjaxBehaviorFactory AjaxBehaviorFactory() {
        return new AjaxBehaviorFactory();
    }

    @Bean @Scope("prototype")
    public AgeOccupationAjaxBehavior ageOccupationAjaxBehavior() {
        return new AgeOccupationAjaxBehavior();
    }

    @Bean @Scope("prototype")
    public PaymentMethodAjaxBehavior paymentMethodAjaxBehavior() {
        return new PaymentMethodAjaxBehavior();
    }

    @Bean
    public Name foo() {
        return new Name();
    }
}
