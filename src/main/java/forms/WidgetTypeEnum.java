package forms;

public enum WidgetTypeEnum {

    TEXT_FIELD(),
    DATE(),
    DATE_LABEL(),
    YES_NO(),
    CHECKBOX(),
    LABEL(),
    BUTTON(),
    RADIO_GROUP(),
    GROUP(),
    SELECT("selectpicker"),
    DIALOG_BUTTON(),
    DIALOG(),
    CONTAINER(),
    DIALOG_SUBMIT_BUTTON(),
    ADDRESS(),
    FORM();

    String pluginName;

    WidgetTypeEnum(String pluginName) {
        this.pluginName = pluginName;
    }
    WidgetTypeEnum() {
        this.pluginName = null;
    }
    // TODO : add plugin name parameter to constructors.
    public String getPluginName() {
        return pluginName;
    }

}
