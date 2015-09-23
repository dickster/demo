package forms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans {

    @Bean
    public Toolkit toolkit() {
        return new Toolkit();
    }

    @Bean
    public Theme theme() {
        return new DefaultTheme();
    }

    @Bean
    public WfFactory WorkflowFactory() {
        return new WfFactory();
    }

}

