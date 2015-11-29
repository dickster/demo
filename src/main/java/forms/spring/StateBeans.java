package forms.spring;

import forms.WfAjaxHandler;
import forms.WfFormState;
import forms.impl.AgeOccupationAjaxHandler;
import forms.impl.PizzaState1;
import forms.impl.PizzaState2;
import forms.impl.State1;
import forms.impl.StateA;
import forms.impl.StateAx;
import forms.impl.StateAy;
import forms.impl.StateB;
import forms.impl.StateC;
import forms.impl.StateError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateBeans {

    @Bean
    public WfAjaxHandler ageOccupationAjaxHandler() {
        return new AgeOccupationAjaxHandler();
    }

    @Bean
    public WfFormState stateA() {
        return new StateA().withAjaxHandlers(ageOccupationAjaxHandler());
    }
    @Bean
    public PizzaState1 pizza1() {
        return new PizzaState1();
    }
    @Bean
    public PizzaState2 pizza2() {
        return new PizzaState2();
    }
    @Bean
    public WfFormState state1() {
        return new State1();
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
