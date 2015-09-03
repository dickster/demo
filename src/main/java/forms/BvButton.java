package forms;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;

public class BvButton extends Button implements HasWidgetOptions {


    public BvButton(String id, IModel<String> model) {
        super(id, model);
    }

    @Override
    public WidgetOptions getOptions() {
        return null;
    }
}
