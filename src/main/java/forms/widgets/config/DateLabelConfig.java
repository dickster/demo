package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.widgets.DateLabel;

public class DateLabelConfig extends FormComponentConfig<DateLabel> {

    // add timeago options here.
    public DateLabelConfig(String property) {
        super(property, WidgetTypeEnum.DATE_LABEL);
    }

    @Override
    public DateLabel create(String id) {
        return new DateLabel(id, this);
    }


}
