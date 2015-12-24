package forms.util;

import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.renderStrategy.DeepChildFirstVisitor;
import org.apache.wicket.util.visit.IVisit;

public class ComponentFinder<T extends Component> {

    private T visitorKludge = null;

    public T find(Page page, final String name) {
        // argh...because the visitor doesn't support generics we have to work around getting a return value.
        // suggest : make a copy that supports generics = EZDeepChildFirstVisitor<Component, Component>();
        visitorKludge = null;   // unfortunately, can't use a local var for this...boo!
        page.visitChildren(Component.class, new DeepChildFirstVisitor() {
            @Override
            public void component(Component component, IVisit<Void> visit) {
                if (component instanceof HasConfig) {
                    String n = ((HasConfig)component).getConfig().getId();
                    if (name.equals(n)) {
                        visitorKludge = (T) component;
                        visit.stop();
                    }
                }
            }

            @Override
            public boolean preCheck(Component component) {
                return true;
            }
        });
        return visitorKludge;
    }

}
