package forms;

public enum WidgetTypeEnum {

    TEXT_FIELD,
    DATE,
    DATE_LABEL,
    YES_NO,
    CHECKBOX,
    LABEL,
    BUTTON,
    RADIO_GROUP,
    GROUP,
    SELECT, DIALOG_BUTTON, DIALOG, CONTAINER, DIALOG_SUBMIT_BUTTON;

    // TODO : add plugin name parameter to constructors.
    public String getPluginName() {
        return this.name().toLowerCase();
    }

}
