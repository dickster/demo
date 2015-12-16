package forms.impl;

import forms.config.ButtonConfig;
import forms.config.FormConfig;
import forms.config.LabelConfig;
import forms.config.TextFieldConfig;

public class ReferFormConfig extends FormConfig {

    public ReferFormConfig() {
        super("REFER_FORM");
        addConfigs();
    }

    private void addConfigs() {
        // TODO : create & use an icon config!
        with(new LabelConfig("You have the following issues with your application...").withIcon("fa-thumbs-down").name("l1").withCss("h3 text-danger"));
        with(new LabelConfig("errors.html", null).withHtmlStrings().name("l2").withCss("h4"));
        with(new LabelConfig("Please enter your phone number. a broker will phone you to process your application<br><br>").withHtmlStrings().name("l3"));
        with(new LabelConfig("phone number").name("l4"));
        with(new TextFieldConfig("insured.contact.phone").name("l5"));
        with(new ButtonConfig("ok"));
    }
}
