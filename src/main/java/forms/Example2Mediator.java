package forms;

import forms.DynamicForm.EventPropogation;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class Example2Mediator implements Mediator {

    public static final String COUNTRY = "country";

    @Override
    public EventPropogation before(Component c, Object event, AjaxRequestTarget target) {
        Component covergePanel = /*blah blah blah */null;
        if (c.getId().equals(COUNTRY) && event.equals("onchange")) {
            if (c.getDefaultModelObject().equals("US")) {
                setUsCoverageValues();
                setZipCode("901473");
            } else {
                setOtherCoverageValues();
                setPostalCode("M7U9P4");
            }
            target.add(covergePanel);
        }
        return EventPropogation.EVENT_PROPOGATE;
    }

    @Override
    public void after(Component c, Object event, AjaxRequestTarget target) {
        target.appendJavaScript("alert('country was changed');");
    }

    // do you stuff that affects other components in these methods
    private void setZipCode(String s) { }
    private void setPostalCode(String s) { }
    private void setOtherCoverageValues() { }
    private void setUsCoverageValues() { }

}
