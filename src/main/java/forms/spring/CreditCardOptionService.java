package forms.spring;

import com.google.common.collect.Lists;

import java.util.List;

public class CreditCardOptionService implements SelectOptionsService<String> {
    private List<String> options = Lists.newArrayList("MasterCard", "AMEX", "VISA" );

    // TODO : create a service that will read these from a .js variable.
    // although it's better to read from a cached url.
    @Override
    public List<String> getOptions() {
        return options;
    }

    public CreditCardOptionService withOptions(String... options) {
        this.options = Lists.newArrayList(options);
        return this;
    }


}
