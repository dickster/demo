package forms.widgets;

import forms.WfEvent;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import javax.annotation.Nullable;

// just a sample class...to show how custom events can be added.
public class WfPostalCodeChangedEvent extends WfEvent {

    public WfPostalCodeChangedEvent(@Nullable Object obj, AjaxRequestTarget target, Component component) {
        super(obj, target, component);
    }
}
