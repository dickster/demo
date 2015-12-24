package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;
import forms.widgets.config.ListConfig;

public class Pizza1Config extends FormConfig {

    public static final String PIZZA_1_CONFIG = "PIZZA-1";

    public Pizza1Config() {
        super(PIZZA_1_CONFIG);
        addConfigs();
        withTitle("Delicious Pizza");
    }

    private void addConfigs() {
        with(new ListConfig("pizzas")
            .with(new LabelConfig("desc", true))
            .with(new LabelConfig("price", true)));
        with(new ButtonConfig("ok"));

//        with(new MultipleSelectPickerConfig("toppings")
//               // .withChoices(Lists.newArrayList("pepperoni", "cheese", "sausage", "onions", "bacon", "pineapple", "mushrooms", "jello", "spinach"))
//                .withChoices(new ToppingsService())
//                .withAttribute("title", "pizza toppings")
//                .withAttribute("data-size", "3")
//                .withAttribute("data-selected-text-format", "count>2"));
//
//        with(new LabelConfig("City"));
//        with(new SelectPickerConfig("city")
//                .withOptions(Lists.newArrayList("new york", "chicago", "sydney", "riyadh", "mumbai"))
//                .withAttribute("dropup", "")
//                .withAttribute("data-live-search", "true"));
//
//        with(new LabelConfig("Pizza Size"));
//        with(new SelectPickerConfig("size")
//                .withOptions(Lists.newArrayList("small", "medium", "large"))
//                .withCss("show-menu-arrow"));
//
//        with(new LabelConfig("Tip"));
//        with(new SelectPickerConfig("tip")
//                .withOptions(Lists.newArrayList("10%", "15%", "20%"))
//                .withAttribute("data-width", "100px"));
//
//        with(new LabelConfig("Food Allergies"));
//        with(new SelectPickerConfig("tip")
//                .withOptions(Lists.newArrayList("10%", "15%", "20%"))
//                .withAttribute("disabled", "")
//                .withAttribute("data-width", "100px"));
//
//        with(new ButtonConfig("Place Order"));
    }
}
