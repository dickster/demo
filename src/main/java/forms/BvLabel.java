package forms;

import org.apache.wicket.markup.html.basic.Label;

import javax.annotation.Nonnull;

public class BvLabel extends Label implements HasJsonOptions {

    public BvLabel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Nonnull
    public JsonOptions getOptions() {
        return null;
    }

}
