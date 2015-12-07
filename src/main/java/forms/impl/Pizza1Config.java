package forms.impl;

import com.google.common.collect.Lists;
import forms.config.ButtonConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;
import forms.config.MultipleSelectPickerConfig;
import forms.config.SelectPickerConfig;

public class Pizza1Config extends FormConfig {

    public static final String PIZZA_1_CONFIG = "PIZZA-1";


    public Pizza1Config() {
        super(PIZZA_1_CONFIG);
        addConfigs();
        withTitle("Delicious Pizza");
    }

    private void addConfigs() {

        with(new LabelConfig("Toppings"));
        with(new MultipleSelectPickerConfig("toppings")
                .withChoices(Lists.newArrayList("pepperoni", "cheese", "sausage", "onions", "bacon", "pineapple", "mushrooms", "jello", "spinach"))
                .withAttribute("title", "pizza toppings")
                .withAttribute("data-size", "3")
                .withAttribute("data-selected-text-format", "count>2"));

        with(new LabelConfig("City"));
        with(new SelectPickerConfig("city")
                .withChoices(Lists.newArrayList("new york", "chicago", "sydney", "riyadh", "mumbai"))
                .withAttribute("dropup", "")
                .withAttribute("data-live-search", "true"));

        with(new LabelConfig("Pizza Size"));
        with(new SelectPickerConfig("size")
                .withChoices(Lists.newArrayList("small", "medium", "large"))
                .withCss("show-menu-arrow"));

        with(new LabelConfig("Tip"));
        with(new SelectPickerConfig("tip")
                .withChoices(Lists.newArrayList("10%", "15%", "20%"))
                .withAttribute("data-width", "100px"));

        with(new LabelConfig("Food Allergies"));
        with(new SelectPickerConfig("tip")
                .withChoices(Lists.newArrayList("10%", "15%", "20%"))
                .withAttribute("disabled", "")
                .withAttribute("data-width", "100px"));

        with(new ButtonConfig("Place Order"));
    }
}