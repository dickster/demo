package forms.spring;

import com.google.common.collect.Lists;

import java.util.List;

public class CountryOptionService implements SelectChoicesService<String> {
    private List<String> countries = Lists.newArrayList("Canada", "USA", "Mexico" );

    @Override
    public List<String> getChoices() {
        return countries;
    }

    public CountryOptionService withChoices(String... countries) {
        this.countries = Lists.newArrayList(countries);
        return this;
    }
}
