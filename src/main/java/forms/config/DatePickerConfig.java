package forms.config;

import forms.WidgetTypeEnum;

import java.util.Date;

public class DatePickerConfig extends WidgetConfig {

    private Date min;
    private Date max;

    public DatePickerConfig(String property) {
        super(property, WidgetTypeEnum.DATE);
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
