package forms.impl;

import forms.WfAjaxEvent;
import forms.WfAjaxHandler;
import forms.util.WfAjaxEventPropagation;
import forms.util.WfUtil;
import org.apache.wicket.Page;

import javax.inject.Inject;

public class DemoAjaxHandler extends WfAjaxHandler {
    private @Inject WfUtil wfUtil;

    @Override
    public WfAjaxEventPropagation handleAjax(WfAjaxEvent event) {
        Page page = event.getComponent().getPage();
        event.getTarget().prependJavaScript("$('#progress').show();");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        event.getTarget().appendJavaScript("$('#progress').hide();");
        return WfAjaxEventPropagation.CONTINUE;
    }
}
