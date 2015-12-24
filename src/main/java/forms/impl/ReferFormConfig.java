package forms.impl;

import forms.widgets.config.ButtonConfig;
import forms.widgets.config.FormConfig;
import forms.widgets.config.LabelConfig;
import forms.widgets.config.TextFieldConfig;

public class ReferFormConfig extends FormConfig {

    public ReferFormConfig() {
        super("REFER_FORM");
        addConfigs();
    }

    private void addConfigs() {
        // TODO : create & use an icon config!
        with(new LabelConfig("You have the following issues with your application...").name("l1").withCss("h3 text-danger"));
        with(new LabelConfig("errors", "errors.html" ).withHtmlStrings().name("l2").withCss("h4"));
        with(new LabelConfig("Please enter your phone number. a broker will phone you to process your application<br><br>").withHtmlStrings().name("l3"));
        with(new LabelConfig("phone number").name("l4"));
        with(new TextFieldConfig("insured.contact.phone").name("l5"));
        with(new ButtonConfig("ok"));
    }
}
