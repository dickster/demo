package forms.widgets;

import org.apache.wicket.markup.html.form.TextField;

import java.util.Date;

public class DatePanel extends TextFieldPanel {

    public DatePanel(String id) {
        super(id);
        setRenderBodyOnly(true);
        add(new TextField<Date>("text"));
        // TODO : later hook up proper date picker.
    }
}
