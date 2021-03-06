package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;

public class Pizza2Config extends FormConfig {

    public static final String PIZZA_2_CONFIG = "PIZZA-2";

    public Pizza2Config() {
        super(PIZZA_2_CONFIG);
        addConfigs();
        withTitle("Pizza Confirmation");
    }

    private void addConfigs() {
        withConfig(new LabelConfig("Toppings"));
        withConfig(new LabelConfig("toppings", false));
        with(new ButtonConfig("Thanks, Come again."));
    }
}
