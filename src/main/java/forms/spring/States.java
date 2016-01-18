package forms.spring;

import forms.impl.ConfirmationState;
import forms.impl.InfoState;
import forms.impl.PaymentState;
import forms.impl.PizzaState1;
import forms.impl.PizzaState2;
import forms.impl.ReferState;
import forms.impl.RejectedState;
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
public class States {

    @Bean
    public InfoState infoState() {
        return new InfoState();
    }

    @Bean
    public PaymentState paymentState() {
        return new PaymentState();
    }

    @Bean
    public ReferState referState() {
        return new ReferState();
    }

    @Bean
    public RejectedState rejectedState() {
        return new RejectedState();
    }

    @Bean
    public ConfirmationState confirmationState() {
        return new ConfirmationState();
    }

    @Bean
    public StateA stateA() {
        return new StateA();
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
    public State1 state1() {
        return new State1();
    }

    @Bean
    public StateC stateC() {
        return new StateC();
    }

    @Bean
    public StateB stateB() {
        return new StateB();
    }

    @Bean
    public StateAx stateAx() {
        return new StateAx();
    }

    @Bean
    public StateAy stateAy() {
        return new StateAy();
    }

    @Bean
    public StateError stateError() {
        return new StateError();
    }

}
