package forms;

import forms.util.WfAjaxEventPropagation;

import java.io.Serializable;

public interface WfAjaxHandler extends Serializable {
    public WfAjaxEventPropagation handleAjax(WfAjaxEvent event);
}
