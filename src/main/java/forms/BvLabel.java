package forms;

import com.google.common.collect.Lists;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;

import javax.annotation.Nonnull;
import java.util.List;

public class BvLabel extends Label implements Ajaxable, EasyWidget {

    private Ajaxable ajaxHandler = new DynamicAjaxHandler<BvLabel>(this);

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

    @Override
    public void respond(AjaxRequestTarget target, String ajaxEvent) {
       ajaxHandler.respond(target, ajaxEvent);
    }

    @Override
    public void addEvent(String event) {
        ajaxHandler.addEvent(event);
    }

}
