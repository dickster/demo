package forms.spring;

import forms.WfAjaxHandler;
import forms.WfFormState;
import forms.impl.StateA;import forms.impl.StateAjaxHandler;import forms.impl.StateAx;import forms.impl.StateAy;import forms.impl.StateB;import forms.impl.StateC;import forms.impl.StateError;import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateBeans {

    @Bean
    public WfAjaxHandler stateHandler() {
        return new StateAjaxHandler();    }

    @Bean
    public WfFormState stateA() {
        return new StateA().withAjaxHandlers(stateHandler());
    }
    @Bean
    public WfFormState stateC() {
        return new StateC();    }
    @Bean
    public WfFormState stateB() {
        return new StateB();    }
    @Bean
    public WfFormState stateAx() {
        return new StateAx();    }
    @Bean
    public WfFormState stateAy() {
        return new StateAy();    }
    @Bean
    public WfFormState stateError() {
        return new StateError();    }

}
