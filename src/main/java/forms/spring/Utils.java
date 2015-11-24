package forms.spring;

import forms.DefaultTheme;
import forms.DefaultToolkit;
import forms.Theme;
import forms.Toolkit;
import forms.WfAjaxHandler;
import forms.WfFactory;
import forms.impl.CommercialWorkflow;
import forms.impl.DemoAjaxHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class Utils {

    @Bean
    public Toolkit toolkit() {
        return new DefaultToolkit();
    }

    @Bean
    public WfAjaxHandler demoHandler() {
        return new DemoAjaxHandler();
    }

    @Bean @Scope("prototype")
    public CommercialWorkflow commercialWorkflow() {
        CommercialWorkflow workflow = new CommercialWorkflow();
        workflow.withAjaxHandlers(demoHandler());
        return workflow;
    }

    @Bean
    public Theme theme() {
        return new DefaultTheme();
    }

    @Bean @Scope("prototype")
    public WfFactory WorkflowFactory() {
        return new WfFactory();
    }

}

