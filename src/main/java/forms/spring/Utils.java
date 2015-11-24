package forms.spring;

import forms.Mediator;
import forms.util.WfUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Utils {

    @Bean
    public WfUtil wfUtil() {
        return new WfUtil();
    }

    @Bean
    public Mediator mediator() {
        return new Mediator();
    }


}

