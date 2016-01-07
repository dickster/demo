package forms.impl;


import com.google.common.collect.Lists;
import demo.resources.Resource;
import forms.DefaultTheme;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.util.List;

public class CustomTheme extends DefaultTheme {

    private static final ResourceReference CSS = new CssResourceReference(Resource.class,"custom.css");

    @Override
    public String getCssClass() {
        return "derek";
    }

    @Nonnull
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

    @Nonnull
    public List<? extends HeaderItem> getCustomHeaderItems() {
        return Lists.newArrayList(CssHeaderItem.forReference(CSS));
    }

    public void apply(Component component) {
        // ; nothing here yet...
    }

}