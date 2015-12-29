package forms.spring;

import forms.widgets.config.LabelConfig;
import org.apache.wicket.Component;

public class SpecialFormatter extends LabelFormatter {

    // TODO : add icon formatter.  IconFormatter(justification (TBLR?, icon, size)???
    @Override
    public String format(Component component, LabelConfig config) {
        String format = "this is special...%d : %s";
        return String.format(format, (int)(Math.random()*1000), config.getProperty() );
    }
}
