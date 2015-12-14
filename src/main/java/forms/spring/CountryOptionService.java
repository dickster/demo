package forms.spring;

import com.google.common.collect.Lists;

import java.util.List;

public class CountryOptionService implements SelectOptionsService<String> {
    private List<String> countries = Lists.newArrayList("Canada", "USA", "Mexico" );

    @Override
    public List<String> getOptions() {
        return countries;
    }

    public CountryOptionService withChoices(String... countries) {
        this.countries = Lists.newArrayList(countries);
        return this;
    }
}
