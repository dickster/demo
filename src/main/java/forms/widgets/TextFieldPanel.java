package forms.widgets;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

public class TextFieldPanel<T> extends Panel {

    public TextFieldPanel(String id) {
        super(id);
        setRenderBodyOnly(true);
        add(new TextField<T>("text"));
        // todo : add help box.  shows error & messages.
    }
}
