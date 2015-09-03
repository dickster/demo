package forms;

import forms.DynamicForm.EventPropogation;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nullable;

public class AjaxMediator implements Runnable {

    private final Runnable callback;
    private final Component component;
    private final AjaxRequestTarget target;
    private final Object event;
    private final Mediator mediator;

    public AjaxMediator(Component component, Object event, AjaxRequestTarget target, @Nullable Runnable callback) {
        this.component = component;
        this.event = event;
        this.target = target;
        this.callback  = callback;
        this.mediator = findParentMediatorFor(component);
    }

    public AjaxMediator(Component component, Object event, AjaxRequestTarget target) {
        this(component, event, target, null);
    }

    private Mediator findParentMediatorFor(final Component component) {
        component.visitParents(MarkupContainer.class, new IVisitor<MarkupContainer, Object>() {
            Mediator mediator = null;
            @Override
            public void component(MarkupContainer parent, IVisit<Object> visit) {
                if (parent instanceof Mediator) {
                    if (mediator != null) {
                        throw new IllegalStateException("component " + component.getId() + " has more than more than one mediator in its hierarchy");
                    }
                    mediator = (Mediator) parent;
                }
            }
        });
        return mediator == null ?
                new NopMediator() : mediator;
    }

    @Override
    public void run() {
        if (EventPropogation.EVENT_STOP.equals(mediator.before(component, event, target))) {
            return;  // NOTE : if mediator vetos event, event handling will stop here without calling delegate.
        }
        if (callback!=null) {
            callback.run();
        }
        mediator.after(component, event, target);
        return;
    }




    class NopMediator implements Mediator {

        @Override public EventPropogation before(Component c, Object event, AjaxRequestTarget target) {
            return EventPropogation.EVENT_PROPOGATE;
        }

        @Override public void after(Component c, Object event, AjaxRequestTarget target) { }
    }



}

