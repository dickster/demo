package forms;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class StateBeans {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WfState dummyState() {
        return new WfState("production");
    }
}
