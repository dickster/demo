package forms.widgets;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;

public class CheckBoxPanel extends Panel {

    public CheckBoxPanel(String id) {
        super(id);
        setRenderBodyOnly(true);
        add(new CheckBox("checkbox"));
    }
}
