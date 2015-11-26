package forms.spring;

import forms.DefaultTheme;
import forms.DefaultToolkit;
import forms.Theme;
import forms.Toolkit;
import forms.WfAjaxHandler;
import forms.WfFactory;
import forms.impl.CommercialWorkflow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;


//import javax.inject.Inject;
//import javax.inject.Scope;


@Configuration
public class Application {

//    private @Autowired StateBeans stateBeans;
//    private @Autowired Utils utils;
    //
    private @Inject @Named("demoAjaxHandler") WfAjaxHandler demoHandler;

    @Bean
    public Toolkit toolkit() {
        return new DefaultToolkit();
    }

    @Bean
    @Scope("prototype")
    public CommercialWorkflow commercialWorkflow() {
        CommercialWorkflow workflow = new CommercialWorkflow();
        workflow.withAjaxHandlers(demoHandler);
        return workflow;
    }

    @Bean
    public Theme theme() {
        return new DefaultTheme();
    }

    @Bean
    //@Scope("prototype")
    public WfFactory WorkflowFactory() {
        return new WfFactory();
    }

}

