package forms.spring;

import forms.util.WfUtil;
import forms.widgets.config.LabelConfig;
import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;

public class LabelFormatter {

    private @Inject StringLoader stringLoader;

    public String format(Component component, LabelConfig config) {
        String property = config.getProperty();

        if (config.isUseParentModel()) {
            return component.getDefaultModelObjectAsString();
        }

//        if (property.indexOf(".")==-1) {
//            System.out.println("hmm...you should probably use keys.  using literal value " + property + " for now");
//            return literal(property);
//        }

        String text = stringLoader.get(config.getProperty());
        if (!config.getData().isEmpty()) {
            String[] args = getArgs(component, config);
            text = MessageFormat.format(text, args);
        }

        return text;
    }

    private String[] getArgs(Component component, LabelConfig config) {
        Object model = WfUtil.getWorkflow(component).getModel();
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
