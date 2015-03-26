package forms;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;


public class DynamicAjaxHandler<T extends Component & EasyWidget> implements Ajaxable {

    private T widget;

    public DynamicAjaxHandler(T widget) {
        this.widget = widget;
    }

    @Override
    public void respond(AjaxRequestTarget target, String ajaxEvent) {
        ; //override to do stuff here...
        target.add(widget);
    }

    @Override
    public void addEvent(String event) {
        widget.getOptions().withAjaxEvent(event);
    }

    public void addEvent(AjaxEventType event) {
        widget.getOptions().withAjaxEvent(event);
    }

}
