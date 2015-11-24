package forms.impl;

import forms.WfAjaxEvent;
import forms.WfAjaxHandler;
import forms.util.WfAjaxEventPropagation;

public class StateAjaxHandler extends WfAjaxHandler {

    @Override
    public WfAjaxEventPropagation handleAjax(WfAjaxEvent event) {
        System.out.println("state handler called");
        return WfAjaxEventPropagation.CONTINUE;
    }
}
