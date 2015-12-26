package forms.spring;

import forms.DefaultTheme;
import forms.DefaultToolkit;
import forms.DefaultWidgetFactory;
import forms.Theme;
import forms.Toolkit;
import forms.WfFactory;
import forms.WidgetFactory;
import forms.widgets.config.Config;
import forms.impl.CommercialWorkflow;
import forms.impl.PizzaWorkflow;
import forms.impl.TestWorkflow;
import org.apache.wicket.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Nonnull;

//import javax.inject.Inject;
//import javax.inject.Scope;


@Configuration
public class Application {

    @Bean
    public Toolkit toolkit() { return new DefaultToolkit(); }

    @Bean
    public SpecialFormatter specialFormatter() {
        return new SpecialFormatter();
    }

    @Bean
    public StringLoader stringLoader() {
        return new StringLoader();
    }

    @Bean
    public LabelFormatterFactory labelFormatterFactory() {
        LabelFormatterFactory factory = new LabelFormatterFactory();
        factory.allowForNulls(defaultLabelFormatter());
        return factory;
    }

    @Bean
    protected LabelFormatter defaultLabelFormatter() {
        return new LabelFormatter();
    }

    @Bean
    @Scope("prototype")
    public CommercialWorkflow commercialWorkflow() {
        CommercialWorkflow workflow = new CommercialWorkflow();
        return workflow;
    }

    @Bean
    @Scope("prototype")
    public TestWorkflow testWorkflow() {
        TestWorkflow workflow = new TestWorkflow();
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
            @Nonnull
            @Override
            public Component create(String id, @Nonnull Config config) {
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

