package forms.config;

import forms.WidgetTypeEnum;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;

import java.util.Date;

public class DatePickerConfig extends FormComponentConfig {

    private Date min;
    private Date max;

    public DatePickerConfig(String property) {
        super(property, WidgetTypeEnum.DATE);
    }

    @Override
    public Component create(String id) {
        return new WebMarkupContainer(id);
    }

    public Date getMin() {
        return min;
    }

    public DatePickerConfig min(Date min) {
        this.min = min;
        return this;
    }

    public Date getMax() {
        return max;
    }

    public DatePickerConfig max(Date max) {
        this.max = max;
        return this;
    }
}
