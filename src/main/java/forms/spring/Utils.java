package forms.spring;

import forms.Mediator;
import forms.util.ConfigGson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utils {

    @Bean
    public SelectOptionsProvider<String> northAmericaCountryOptions() {
        return new CountryOptionService();
    }

    @Bean
    public SelectOptionsProvider<String> defaultCountryOptions() {
        return new CountryOptionService()
                .withChoices("Germany", "France", "Russia", "Italy", "Switzerland", "Cambodia", "Chile", "North Korea", "Australia");
    }

    @Bean
    public ConfigGson configGson() {
        return new ConfigGson();
    }

    @Bean
    public Mediator mediator() {
        return new Mediator();
    }


}

