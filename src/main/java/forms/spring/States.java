package forms.spring;

import forms.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class States {

    @Bean
    public DemoState1 demoState1() {
        return new DemoState1();
    }

    @Bean
    public DemoState2 demoState2() {
        return new DemoState2();
    }

    @Bean
    public DemoState3 demoState3() {
        return new DemoState3();
    }

    @Bean
    public DemoState4 demoState4() {
        return new DemoState4();
    }

    @Bean
    public DemoState5 demoState5() {
        return new DemoState5();
    }

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
