package forms;

import org.apache.wicket.Component;

public class BvLabelConfig extends WidgetConfig<BvLabel> {

    private String color;

    public BvLabelConfig(String color, String property, String label) {
        super(property, label, WidgetTypeEnum.TEXT_FIELD);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
