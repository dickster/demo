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

    @Bean
    public WfFactory WorkflowFactory() {
        return new WfFactory();
    }

    @Bean
    public WfStateFactory wfStateFactory() {
        // create a state factory that optionally checks for groovy files and runs them.
        // eg  .../Commercial/AI.groovy  could contain source for "AI" state in commercial workflow.
        return new WfStateFactory();
    }

    @Bean
    public WfState stateA() {
        return new WfState("A");
    }

}

