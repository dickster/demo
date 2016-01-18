package forms.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import forms.spring.SelectOptionsProvider;

import javax.annotation.Nullable;
import java.util.List;

public class ToppingsService implements SelectOptionsProvider<String> {

    List<String> toppings = Lists.newArrayList(
            "pepperoni",
            "cheese",
            "sausage",
            "onions",
            "bacon",
            "pineapple",
            "mushrooms",
            "jello",
            "spinach",
            "tomato",
            "prosciutto",
            "ham",
            "basil",
            "potato",
            "egg",
            "anchovies"
    );

    @Override
    public List<String> getOptions() {
        Predicate<String> randomToppingsPredicate = new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return Math.random()>.75;
            }
        };
        return Lists.newArrayList( Collections2.filter(toppings, randomToppingsPredicate) );
    }
}
