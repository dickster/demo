package forms;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.List;


// should make the WidgetOptions have optional Ajaxable interface???
public interface Ajaxable {

    void respond(AjaxRequestTarget target, String ajaxEvent);

    void addEvent(String event);
}
