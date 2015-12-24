package forms.spring;

import javax.inject.Inject;

public class LabelFormatterFactory extends WfBeanFactory<LabelFormatter> {

    private @Inject LabelFormatter defaultLabelFormatter;

    public LabelFormatterFactory() {
        super(LabelFormatter.class);
    }

}
