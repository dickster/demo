package forms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public Toolkit toolkit(String foo) {
        return new Toolkit();
    }

    @Bean
    public Theme theme() {
        return new DefaultTheme();
    }

    @Bean(name="Comml")  // give bean specific name if you want, will use method name by default.
    public Workflow commercialWorkflow() {
        return new CommercialWorkflow();
    }

    @Bean
    public WfFactory WorkflowFactory() {
        return new WfFactory();
    }

    @Bean
    public WfStateFactory wfStateFactory() {
        return new WfStateFactory();
    }

}

