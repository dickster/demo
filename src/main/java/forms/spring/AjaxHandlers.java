package forms.spring;

import forms.WfAjaxHandler;
import forms.ajax.AgeOccupationAjaxHandler;
import forms.ajax.PaymentMethodAjaxHandler;
import forms.ajax.WfDelayedEventAjaxHandler;
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
    public WfAjaxHandler ageOccupationAjaxHandler() {
        return new AgeOccupationAjaxHandler();
    }

    @Bean @Scope("prototype")
    public WfAjaxHandler creditCardAjaxHandler() {
        return new WfDelayedEventAjaxHandler("change inputchange");
    }

    @Bean @Scope("prototype")
    public WfAjaxHandler paymentMethodAjaxHandler() {
        return new PaymentMethodAjaxHandler();
    }

}
