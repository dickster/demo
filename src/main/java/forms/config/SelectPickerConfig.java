package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.SelectPicker;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.util.List;

public class SelectPickerConfig extends WidgetConfig {

    private List choices;


    public SelectPickerConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.SELECT);
    }

    public List getChoices() {
        return choices;
    }

    public SelectPickerConfig withChoices(List<?> choices) {
        this.choices = choices;
        return this;
    }

    @Override
    public Component create(String id) {
        return new SelectPicker(id, this);
    }

}
