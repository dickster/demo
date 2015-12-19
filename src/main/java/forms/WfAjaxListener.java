package forms;

import com.google.common.eventbus.Subscribe;

public abstract class WfAjaxListener {

    @Subscribe
    public abstract void onAjax(WfAjaxEvent event);

}
