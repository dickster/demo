package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;


public class DefaultAjaxHandler<T extends Component & Ajaxable> implements Ajaxable {

    private T widget;

    public DefaultAjaxHandler(T widget) {
        this.widget = widget;
    }

    @Override
    public void respond(AjaxRequestTarget target, String ajaxEvent) {
        widget.respond(target, ajaxEvent);
        target.add(widget);
    }

    @Override
    public void addEvent(final String event) {
        widget.add(new AjaxEventBehavior(event) {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                widget.respond(target, event);
            }
        });
    }

}
