package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import forms.Mediator.MediatorType;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.List;



public class MediatedAjaxEventBehavior extends AjaxEventBehavior{

    private List<MediatorType> callbacks = Lists.newArrayList(MediatorType.POST);


    public MediatedAjaxEventBehavior(String event) {
        super(event);
        Session session;
    }

    public MediatedAjaxEventBehavior withTypes(MediatorType... type) {
        Preconditions.checkArgument(type.length>0, "mediator must be called either before and/or after method");
        callbacks = Lists.newArrayList(type);
        return this;
    }


    @Override
    protected void onEvent(AjaxRequestTarget target) {
        Mediator.mediate(this, target, getComponent(), callbacks);
    }

}
