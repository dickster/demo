package forms;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class SpringBeans {

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Toolkit toolkit() {
        return new DefaultToolkit();
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Theme theme() {
        return new DefaultTheme();
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WfFactory WorkflowFactory() {
        return new WfFactory();
    }

}

