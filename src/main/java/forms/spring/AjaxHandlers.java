package forms.spring;

import forms.WfAjaxBehavior;
import forms.ajax.AgeOccupationAjaxBehavior;
import forms.ajax.PaymentMethodAjaxBehavior;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AjaxHandlers {

    // NOTE : *all* ajax handlers MUST be of Scope("prototype")!!!!!
    //   e.g. @Bean @Scope("prototype") public Foo foo() {...}

    @Bean
    public AjaxHandlerFactory ajaxHandlerFactory() {
        return new AjaxHandlerFactory();
    }

    @Bean @Scope("prototype")
    public WfAjaxBehavior ageOccupationAjaxHandler() {
        return new AgeOccupationAjaxBehavior();
    }

    @Bean @Scope("prototype")
    public WfAjaxBehavior paymentMethodAjaxHandler() {
        return new PaymentMethodAjaxBehavior();
    }
}
