package forms.widgets;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;

public class TextField2 extends TextField {
    public TextField2(String id) {
        super(id);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "text");
        super.onComponentTag(tag);
    }
}
