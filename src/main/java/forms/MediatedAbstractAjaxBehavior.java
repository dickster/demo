package forms;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import forms.Mediator.MediatorType;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.List;

// if you want to broadcast events to a parent mediator.
public abstract class MediatedAbstractAjaxBehavior extends AbstractDefaultAjaxBehavior {

    private List<MediatorType> callbacks = Lists.newArrayList(MediatorType.POST);


    public MediatedAbstractAjaxBehavior() {
        super();
    }

    public MediatedAbstractAjaxBehavior withTypes(MediatorType... type) {
        Preconditions.checkArgument(type.length > 0, "mediator must be called either before and/or after method");
        callbacks = Lists.newArrayList(type);
        return this;
    }

    @Override
    protected final void respond(final AjaxRequestTarget target) {
        Mediator.mediate(this, target, getComponent(), callbacks);
    }

}
