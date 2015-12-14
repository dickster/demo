package forms.spring;

import forms.*;
import forms.config.Config;
import forms.impl.CommercialWorkflow;
import forms.impl.PizzaWorkflow;
import forms.impl.TestWorkflow;
import org.apache.wicket.Component;
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

    private @Inject @Named("ageOccupationAjaxHandler") WfAjaxHandler ageOccupationAjaxHandler;

    @Bean
    public Toolkit toolkit() {
        return new DefaultToolkit();
    }

    @Bean
    @Scope("prototype")
    public CommercialWorkflow commercialWorkflow() {
        CommercialWorkflow workflow = new CommercialWorkflow();
        workflow.withAjaxHandlers(ageOccupationAjaxHandler);
        return workflow;
    }

    @Bean
    @Scope("prototype")
    public TestWorkflow testWorkflow() {
        TestWorkflow workflow = new TestWorkflow();
        // add ajax handlers.
        return workflow;
    }

    @Bean
    @Scope("prototype")
    public PizzaWorkflow pizzaWorkflow() {
        PizzaWorkflow workflow = new PizzaWorkflow();
        return workflow;
    }

    @Bean
    public WidgetFactory customWidgetFactory() {
        return new DefaultWidgetFactory() {
            @Override
            public Component create(String id, Config config) {
                if ("toppings".equals(config.getId())) {
                    config.withAttribute("title", "this is a custom widget factory tooltip");
                    config.withAttribute("style", "border:1px solid blue;");
                }
                return super.create(id, config);
            }
        };
    }

    @Bean
    public WidgetFactory widgetFactory() {
        return new DefaultWidgetFactory();
    }

    @Bean
    public Theme theme() {
        return new DefaultTheme();
    }

    @Bean
    @Scope("prototype")
    public WfFactory WorkflowFactory() {
        return new WfFactory();
    }

}

