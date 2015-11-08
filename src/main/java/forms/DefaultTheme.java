package forms;


import com.google.common.collect.Lists;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.List;
import java.util.Locale;

public class DefaultTheme implements Theme {

    // css references,
    // css class,
    // javascript initialization?
    // name
    // default locale, settings.

    @Override
    public String getCssClass() {
        return getName();
    }

    @Override
    public String getName() {
        return "defaultTheme";
    }

    @Override
    public Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    @Override
    public String getFlavour() {
        return "basic";
    }

    @Override
    public void apply(Form form) {
        form.getPage().add(new AttributeAppender("class", getCssClass()));
        form.visitChildren(Component.class, new IVisitor<Component, Object>() {
            @Override
            public void component(Component component, IVisit<Object> visit) {
                apply(component);
            }
        });
    }

    public List<? extends HeaderItem> getHeaderItems() {
        return Lists.newArrayList(  /*CssHeaderItem.forReference(null-->put something here), */
                                    CssHeaderItem.forCSS("blah blah blah", "myCss"));
    }

    public void apply(Component component) {
        // examples of things to do...

//        // what about apply?() methods for each specific type of component?
//        component.add(new AttributeAppender("class", "motif"));
//        if (component instanceof HasJsonOptions) {
//            HasJsonOptions ezWidget = (HasJsonOptions) component;
//            ezWidget.getOptions()
//                        .withOption("color", "blue")
//                        .withOption("spacing", "large");
//        }
        // typically add javascript to do layout, styling, adding classes, add attributes to widgets,
        //   add listeners to facilitate overlays/dialogs/menus...whatever
    }

}