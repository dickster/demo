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
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Locale;


public class DefaultTheme implements Theme {

    // css references,
    // css class,
    // javascript initialization?
    // name
    // default locale, settings.

    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.min.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");

    private static final CssResourceReference BRAND_CSS = new CssResourceReference(Resource.class,"brand.css");

    private static final CssResourceReference RESET_CSS = new CssResourceReference(Resource.class,"reset.css");
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

    // CAVEAT : override this at your own risk!
    // these files are ones that every implementation should need.
    @Nonnull
    protected /*final*/ List<HeaderItem> getCoreHeaderItems() {
        return Lists.newArrayList(getBodyClassHeaderItem(),
                JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()),
                JavaScriptHeaderItem.forReference(JQUERY_UI_JS),
                CssHeaderItem.forReference(JQUERY_UI_CSS),
                JavaScriptHeaderItem.forReference(BOOTSTRAP_JS),
                CssHeaderItem.forReference(BOOTSTRAP_CSS),
                CssHeaderItem.forReference(RESET_CSS),
                // TODO : these should be moved to inside appropriate components.
                JavaScriptHeaderItem.forReference(DELAYEDEVENT_JS),
                JavaScriptHeaderItem.forReference(CREDITCARD_VALIDATOR_JS),
                JavaScriptHeaderItem.forReference(CREDITCARD_JS),
                JavaScriptHeaderItem.forReference(INPUTGROUP_JS),
                JavaScriptHeaderItem.forReference(TYPEAHEAD_JS)
        );
    }

    @Nonnull
    public final List<? extends HeaderItem> getHeaderItems() {
        List<HeaderItem> result = getCoreHeaderItems();
        result.addAll(getCustomHeaderItems());
        return result;
    }

    @Nonnull
    protected List<? extends HeaderItem> getCustomHeaderItems() {
        return Lists.newArrayList(CssHeaderItem.forReference(BRAND_CSS));
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