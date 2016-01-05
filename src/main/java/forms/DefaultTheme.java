package forms;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import demo.resources.Resource;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Locale;

// TODO : introduce a "CORE" theme with final methods of stuff that *must* be there.
public class DefaultTheme implements Theme {

    // css references,
    // css class,
    // javascript initialization?
    // name
    // default locale, settings.

    private static final ResourceReference BRAND_CSS = new CssResourceReference(Resource.class,"brand.css");
    private static final JavaScriptResourceReference CREDITCARD_VALIDATOR_JS = new JavaScriptResourceReference(Resource.class, "/creditcard/jquery.creditCardValidator.js");
    private static final JavaScriptResourceReference CREDITCARD_JS = new JavaScriptResourceReference(Resource.class, "creditCard.js");
    private static final JavaScriptResourceReference INPUTGROUP_JS = new JavaScriptResourceReference(Resource.class, "inputgroup.js");
    private static final JavaScriptResourceReference DELAYEDEVENT_JS = new JavaScriptResourceReference(Resource.class, "delayedEvent.js");
    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "type_ahead.js");


    @Override
    public String getCssClass() {
        return Joiner.on(' ').skipNulls().join(getName(), getDebugClass());
    }

    @Nonnull
    @Override
    public String getName() {
        return "defaultTheme";
    }

    protected String getDebugClass() {
        switch (Application.get().getConfigurationType()) {
            case DEVELOPMENT:
                return "debugMode";
            case DEPLOYMENT:
            default:
                return null;
        }
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
        form.visitChildren(Component.class, new IVisitor<Component, Object>() {
            @Override
            public void component(Component component, IVisit<Object> visit) {
                apply(component);
            }
        });
    }

    @Nonnull
    public List<? extends HeaderItem> getHeaderItems() {
        return Lists.newArrayList(getBodyClassHeaderItem(),
                CssHeaderItem.forReference(BRAND_CSS),
                JavaScriptHeaderItem.forReference(DELAYEDEVENT_JS),
                JavaScriptHeaderItem.forReference(CREDITCARD_VALIDATOR_JS),
                JavaScriptHeaderItem.forReference(CREDITCARD_JS),
                JavaScriptHeaderItem.forReference(INPUTGROUP_JS),
                JavaScriptHeaderItem.forReference(TYPEAHEAD_JS)
        );
    }

    protected HeaderItem getBodyClassHeaderItem() {
        return OnDomReadyHeaderItem.forScript(
                String.format("$('body').addClass('%s');", getCssClass()));
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