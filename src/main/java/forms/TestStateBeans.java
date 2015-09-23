package forms;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile("test")
public class TestStateBeans {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WfState dummyState() {
        return new WfState("test");
    }

}
