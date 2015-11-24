package forms.impl;

import forms.WfAjaxEvent;
import forms.WfAjaxHandler;
import forms.util.WfAjaxEventPropagation;
import forms.util.WfUtil;

import javax.inject.Inject;

public class DemoAjaxHandler extends WfAjaxHandler {
    private @Inject WfUtil wfUtil;

    @Override
    public WfAjaxEventPropagation handleAjax(WfAjaxEvent event) {
        System.out.println("DEMO HANDLER CALLED FOR " + wfUtil.getComponentName(event.getComponent()));
        return WfAjaxEventPropagation.CONTINUE;
    }
}
