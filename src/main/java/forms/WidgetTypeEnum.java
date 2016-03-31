package forms;

import forms.widgets.*;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;

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
    DIALOG_INVOKING_BUTTON(),
    DIALOG(),
    CONTAINER(),
    DIALOG_SUBMIT_BUTTON(),
    ADDRESS(),
    SECTION("sectionPanel"),
    MULTIPLE_SELECT("selectpicker"),
    DEBUG_BUTTON(),
    TYPEAHEAD("type_ahead"),
    FEEDBACK(),
    CREDIT_CARD("creditCard" ),
    LIST(),
    TAB_PANEL(),
    PHONE_NUMBER(),
    FORM();

    String pluginName;
    private Class<? extends Component> type;

    WidgetTypeEnum() {
        this(null);
    }

    WidgetTypeEnum(String pluginName) {
        this.pluginName = pluginName;
    }

    // TODO : add plugin name parameter to constructors.
    public String getPluginName() {
        return pluginName;
    }

    public Class<? extends Component> getType() {
        return type;
    }

}
