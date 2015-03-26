package forms;

public enum WidgetTypeEnum {

    TEXT_FIELD("easyText", BaseType.PRIMITIVE),
    DATE("easyDate"),
    CHECKBOX("easyCheckBox"),
    LABEL(BaseType.VOID),
    RADIO_GROUP("easyRadio");

    private final BaseType type;
    private final String pluginName;

    WidgetTypeEnum(String pluginName, BaseType type) {
        this.type = type;
        this.pluginName = pluginName;
    }
    WidgetTypeEnum(BaseType type) {
       this(null, type);

    }

    WidgetTypeEnum(String pluginName) {
        this(pluginName, BaseType.PRIMITIVE);
    }

    enum BaseType {ARRAY, PRIMITIVE, VOID};

}
