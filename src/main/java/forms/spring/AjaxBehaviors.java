package forms.spring;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AjaxBehaviors {

    // NOTE : *all* ajax behaviors MUST be of Scope("prototype")!!
    //   e.g. @Bean @Scope("prototype") public Foo foo() {...}

//    @Bean
//    public AjaxBehaviorFactory AjaxBehaviorFactory() {
//        return new AjaxBehaviorFactory();
//    }
//
//    @Bean @Scope("prototype")
//    public AgeOccupationAjaxBehavior ageOccupationAjaxBehavior() {
//        return new AgeOccupationAjaxBehavior();
//    }
//
//    @Bean @Scope("prototype")
//    public PaymentMethodAjaxBehavior paymentMethodAjaxBehavior() {
//        return new PaymentMethodAjaxBehavior();
//    }
//
//    @Bean
//    public Name foo() {
//        return new Name();
//    }
}
