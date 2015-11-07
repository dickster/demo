package forms.config;

import forms.WidgetTypeEnum;

public class DateLabelConfig extends WidgetConfig {

    // add timeago options here.
    public DateLabelConfig(String property, String label) {
        super(property, label, WidgetTypeEnum.DATE_LABEL);
    }


}
