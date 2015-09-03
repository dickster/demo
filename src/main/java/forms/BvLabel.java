package forms;

import org.apache.wicket.markup.html.basic.Label;

import javax.annotation.Nonnull;

public class BvLabel extends Label implements HasWidgetOptions {

    public BvLabel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Nonnull
    public WidgetOptions getOptions() {
        return null;
    }

}
