package forms.spring;

import forms.widgets.config.LabelConfig;
import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;

public class LabelFormatter {

    private @Inject StringLoader stringLoader;
    private @Inject WfNavigator wfNavigator;

    public String format(Component component, LabelConfig config) {

        if (config.isUseParentModel()) {
            return component.getDefaultModelObjectAsString();
        }

        String text = stringLoader.get(config.getProperty());
        if (!config.getData().isEmpty()) {
            String[] args = getArgs(component, config);
            text = MessageFormat.format(text, args);
        }

        return text;
    }

    protected String[] getArgs(Component component, LabelConfig config) {
        Object model = wfNavigator.getWorkflow(component).getModel();
        List<String> data = config.getData();
        String[] result = new String[data.size()];
        for (int i=0;i<data.size();i++) {
            String ognl = data.get(i);
            Object value = new PropertyModel(model, ognl).getObject();
            result[i] = (value==null) ? "" : value.toString();
        }
        return result;
    }

    private String literal(String id) {
        return id;
    }
}
