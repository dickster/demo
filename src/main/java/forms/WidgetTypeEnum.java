package forms;

public enum WidgetTypeEnum {

    TEXT_FIELD("inputGroup"), // TODO : make this a separate widget.
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
    FORM(),
    SECTION("sectionPanel"),
    MULTIPLE_SELECT("selectpicker"),
    DEBUG_BUTTON(),
    TYPEAHEAD("type_ahead"),
    FEEDBACK(),
    CREDIT_CARD("creditCard"),
    LIST(),
    TAB_PANEL(), PHONE_NUMBER();

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
