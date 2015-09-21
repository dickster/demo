package forms;

public enum WidgetTypeEnum {

    TEXT_FIELD("easyText"),
    DATE("easyDate"),
    CHECKBOX("easyCheckBox"),
    LABEL(),
    BUTTON(),
    RADIO_GROUP("easyRadio");

    private final String pluginName;

    WidgetTypeEnum(String pluginName) {
        this.pluginName = pluginName;
    }
    WidgetTypeEnum() {
       this("");
    }

}
