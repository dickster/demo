package forms.widgets.config;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import forms.WidgetTypeEnum;
import forms.widgets.PhoneNumber;

import javax.annotation.Nonnull;

public class PhoneNumberConfig extends FormComponentConfig<PhoneNumber> {

    private String region = "CA";
    private PhoneNumberUtil.PhoneNumberFormat format = PhoneNumberUtil.PhoneNumberFormat.NATIONAL;

    public PhoneNumberConfig(@Nonnull String property) {
        super(property, WidgetTypeEnum.PHONE_NUMBER);
    }

    @Override
    public PhoneNumber create(String id) {
        return new PhoneNumber(id, this);
    }

    public String getRegion() {
        return region;
    }

    public PhoneNumberUtil.PhoneNumberFormat getFormat() {
        return format;
    }
}
