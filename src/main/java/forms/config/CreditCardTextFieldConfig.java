package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.CreditCardTextField;

import javax.annotation.Nonnull;

public class CreditCardTextFieldConfig extends FormComponentConfig<CreditCardTextField> {

    public CreditCardTextFieldConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.CREDIT_CARD);
    }


    @Override
    public CreditCardTextField create(String id) {
        // TODO : replace with clazz.newInstance(id, config);
        return new CreditCardTextField(id,this);
    }
}
