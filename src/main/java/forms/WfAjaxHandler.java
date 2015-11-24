package forms;

import forms.util.WfAjaxEventPropagation;
import forms.util.WfUtil;

import javax.inject.Inject;
import java.io.Serializable;

public abstract class WfAjaxHandler implements Serializable {

    private @Inject WfUtil wfUtil;

    public WfAjaxEventPropagation handleAjax(WfAjaxEvent event) {
        System.out.println("ajax event occurred " + wfUtil.getComponentName(event.getComponent()));
        return WfAjaxEventPropagation.CONTINUE;
    }
}
