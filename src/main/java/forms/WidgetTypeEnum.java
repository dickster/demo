package forms;

import java.util.Date;

public enum WidgetTypeEnum {

    TEXT_FIELD("easyText", String.class),
    DATE("easyDate", Date.class),
    CHECKBOX("easyCheckBox", Boolean.class),
    LABEL(Void.class),
    RADIO_GROUP("easyRadio", Boolean.class);

    private final Class<?> type;
    private final String pluginName;

    WidgetTypeEnum(String pluginName, Class<?> type) {
        this.type = type;
        this.pluginName = pluginName;
    }
    WidgetTypeEnum(Class<?> type) {
       this("", type);
    }

}
