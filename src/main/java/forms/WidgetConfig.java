package forms;

import org.apache.wicket.Component;

public class WidgetConfig<T extends Component> {

    private String property;
    private String label;
    private WidgetTypeEnum type;
    private String acordProperty;

    public WidgetConfig(String property, String label, WidgetTypeEnum type) {
        this.property = property;
        this.label = label;
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public WidgetTypeEnum getType() {
        return type;
    }

    public String getAcordProperty() {
        return acordProperty;
    }

    public String getProperty() {

        return property;
    }
    //other config stuff...visible, css class,

    public WidgetTypeEnum getWidgetType() {
        return type;
    }
}
