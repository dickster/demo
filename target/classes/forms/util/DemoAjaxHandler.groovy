package forms.util

import forms.WfAjaxEvent
import forms.WfAjaxHandler

import javax.inject.Inject

class DemoAjaxHandler implements WfAjaxHandler {

    private @Inject WfUtil wfUtil; // this is a java class inside a groovy bean.

    @Override
    public WfAjaxEventPropagation handleAjax(WfAjaxEvent event) {
        println('hello;');
        event.getTarget().prependJavaScript('$("#progress span").text("groovy!"); $("#progress").show();');
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        event.getTarget().appendJavaScript('$("#progress").hide();');
        return WfAjaxEventPropagation.CONTINUE;
    }

}
