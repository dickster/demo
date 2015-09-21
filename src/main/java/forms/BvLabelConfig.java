package forms;

public class BvLabelConfig extends WidgetConfig {

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
