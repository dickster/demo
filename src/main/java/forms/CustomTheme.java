package forms;


import com.google.common.collect.Lists;
import demo.resources.Resource;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.List;

public class CustomTheme extends DefaultTheme {

    private static final ResourceReference CSS = new CssResourceReference(Resource.class,"custom.css");

    @Override
    public String getCssClass() {
        return "derek";
    }

    @Override
    public String getName() {
        return getCssClass();
    }

    @Override
    public String getFlavour() {
        return "basic";
    }

    @Override
    public void apply(Form form) {
        form.visitChildren(Component.class, new IVisitor<Component, Object>() {
            @Override
            public void component(Component component, IVisit<Object> visit) {
                apply(component);
            }
        });
    }

    public List<? extends HeaderItem> getHeaderItems() {
        return Lists.newArrayList(getBodyClassHeaderItem(), CssHeaderItem.forReference(CSS));
    }

    public void apply(Component component) {
        // examples of things to do...

//        // what about apply?() methods for each specific type of component?
//        component.add(new AttributeAppender("class", "motif"));
        if (component instanceof HasJsonOptions) {
            HasJsonOptions widget = (HasJsonOptions) component;
            widget.getOptions()
                        .withOption("color", "blue")
                        .withOption("spacing", "large");
        }
        // typically add javascript to do layout, styling, adding classes, add attributes to widgets,
        //   add listeners to facilitate overlays/dialogs/menus...whatever
    }

}