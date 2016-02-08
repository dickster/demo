package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.widgets.YesNo;
import org.apache.wicket.model.IModel;

import javax.annotation.Nonnull;

public class YesNoConfig extends FormComponentConfig<YesNo> {

    private String no = "label.no";
    private String yes = "label.yes";

    public YesNoConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.YES_NO);
    }

    @Override
    public YesNo create(String id) {
        return new YesNo(id, this);
    }

    public String getNoLabel() {
        return no;
    }

    public String getYesLabel() {
        return yes;
    }
}
