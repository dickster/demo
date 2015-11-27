package forms.spring;

import forms.Mediator;
import forms.util.ConfigGson;
import forms.util.WfUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;



@Configuration
public class Utils {

    @Bean
    public ConfigGson configGson() {
        return new ConfigGson();
    }

    @Bean @Scope("prototype")
    public WfUtil wfUtil() {
        return new WfUtil();
    }

    @Bean
    public Mediator mediator() {
        return new Mediator();
    }


}

